/**
  * This file is part of VoteBox.
  * 
  * VoteBox is free software: you can redistribute it and/or modify
  * it under the terms of the GNU General Public License version 3 as published by
  * the Free Software Foundation.
  * 
  * You should have received a copy of the GNU General Public License
  * along with VoteBox, found in the root of any distribution or
  * repository containing all or part of VoteBox.
  * 
  * THIS SOFTWARE IS PROVIDED BY WILLIAM MARSH RICE UNIVERSITY, HOUSTON,
  * TX AND IS PROVIDED 'AS IS' AND WITHOUT ANY EXPRESS, IMPLIED OR
  * STATUTORY WARRANTIES, INCLUDING, BUT NOT LIMITED TO, WARRANTIES OF
  * ACCURACY, COMPLETENESS, AND NONINFRINGEMENT.  THE SOFTWARE USER SHALL
  * INDEMNIFY, DEFEND AND HOLD HARMLESS RICE UNIVERSITY AND ITS FACULTY,
  * STAFF AND STUDENTS FROM ANY AND ALL CLAIMS, ACTIONS, DAMAGES, LOSSES,
  * LIABILITIES, COSTS AND EXPENSES, INCLUDING ATTORNEYS' FEES AND COURT
  * COSTS, DIRECTLY OR INDIRECTLY ARISING OUR OF OR IN CONNECTION WITH
  * ACCESS OR USE OF THE SOFTWARE.
 */

package votebox.middle.driver;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Observer;
import java.util.Stack;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.print.PrintService;
import javax.print.attribute.standard.PrinterName;

import auditorium.IAuditoriumParams;

import sexpression.ASExpression;
import sexpression.ListExpression;
import tap.BallotImageHelper;
import votebox.middle.IBallotVars;
import votebox.middle.Properties;
import votebox.middle.ballot.Ballot;
import votebox.middle.ballot.BallotParser;
import votebox.middle.ballot.BallotParserException;
import votebox.middle.ballot.CardException;
import votebox.middle.ballot.IBallotLookupAdapter;
import votebox.middle.ballot.NonCardException;
import votebox.middle.view.IViewFactory;
import votebox.middle.view.ViewManager;

public class Driver {

	private final String _path;

	private final IViewFactory _factory;

	private ViewManager _view;

	private Ballot _ballot;
	
	private boolean _encryptionEnabled;

	private IAdapter _viewAdapter = new IAdapter() {

		public boolean deselect(String uid) throws UnknownUIDException,
				DeselectionException {
			return _view.deselect(uid);
		}

		public Properties getProperties() {
			return _view.getCurrentLayout().getProperties();
		}

		public boolean select(String uid) throws UnknownUIDException,
				SelectionException {
			return _view.select(uid);
		}
	};

	private IAdapter _ballotAdapter = new IAdapter() {

		public boolean deselect(String uid) throws UnknownUIDException,
				DeselectionException {
			return _ballot.deselect(uid);
		}

		public Properties getProperties() {
			return _ballot.getProperties();
		}

		public boolean select(String uid) throws UnknownUIDException,
				SelectionException {
			return _ballot.select(uid);
		}

	};

	private IBallotLookupAdapter _ballotLookupAdapter = new IBallotLookupAdapter() {

		public boolean isCard(String UID) throws UnknownUIDException {
			return _ballot.isCard(UID);
		}

		public String selectedElement(String UID) throws NonCardException,
				UnknownUIDException, CardException {
			return _ballot.selectedElement(UID);
		}

		public boolean exists(String UID) {
			return _ballot.exists(UID);
		}

		public boolean isSelected(String uid) throws UnknownUIDException {
			return _ballot.isSelected(uid);
		}

		public ASExpression getCastBallot() {
			if(!_encryptionEnabled)
				return _ballot.toASExpression();
			
			return _ballot.getCastBallot();
		}

		public int numSelections() {
			return _ballot.getNumSelections();
		}

	};

	public Driver(String path, IViewFactory factory, boolean encryptionEnabled) {
		_path = path;
		_factory = factory;
		_encryptionEnabled = encryptionEnabled;
	}

	public void run(Observer castBallotObserver) {
		IBallotVars vars;
		try {
			vars = new GlobalVarsReader(_path).parse();
		} catch (IOException e) {
			System.err
					.println("The ballot's configuration file could not be found.");
			e.printStackTrace();
			return;
		}
		try {
			_ballot = new BallotParser().getBallot(vars);
		} catch (BallotParserException e) {
			System.err
					.println("The ballot's XML file was unable to be parsed.");
			e.printStackTrace();
			return;
		}
		_ballot.setViewAdapter(_viewAdapter);
		_view = new ViewManager(_ballotAdapter, _ballotLookupAdapter, vars,
				_factory);
		
		if(castBallotObserver != null)
			_view.registerForCastBallot(castBallotObserver);
		
		_view.run();
	}
	
	public void run(){
		run(null);
	}

	public void kill() {
		_view.dispose();
	}
    
    /**
     * Gets this VoteBox instance's view.  Used to allow the caller to register for
     * the cast ballot event in the view manager.
     * @return the view manager
     */
    public ViewManager getView() {
        return _view;
    }

    /**
     * Prints a ballot out on a VVPAT.
     * 
     * @param ballot - ballot in the form ((race-id (race-id (... ))))
     */
    public static void printCommittedBallot(IAuditoriumParams constants, ListExpression ballot, File currentBallotFile) {
    	System.out.println("Printing Committed Ballot");
    	
		final Map<String, Image> choiceToImage = BallotImageHelper.loadImagesForVVPAT(currentBallotFile);
		
		if(choiceToImage == null){
			System.out.println("\tPrinting aborted, no VVPAT images");
			return;
		}
		
		final List<String> choices = new ArrayList<String>();
		for(int i = 0; i < ballot.size(); i++){
			ListExpression choice = (ListExpression)ballot.get(i);
			//System.out.println("\tchoice "+i+": "+choice);
			
			if(choice.size() != 2)
				continue;
			
			if(!(choice.get(1) instanceof ListExpression))
				continue;
			
			if(((ListExpression)choice.get(1)).size() < 1)
				continue;
			
			if(((ListExpression)choice.get(1)).get(0).toString().trim().length() > 0)
				choices.add(((ListExpression)choice.get(1)).get(0).toString());
		}
		
		System.out.println("\tChoices extracted: "+choices);
		
		int totalSize = 0;
		for(int i = 0; i < choices.size(); i++)
			totalSize += choiceToImage.get(choices.get(i)).getHeight(null);
		
		System.out.println("\tPage size determined: "+totalSize);
		
		final int fTotalSize = totalSize;
		
		Printable printedBallot = new Printable(){

			public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
				System.out.println("\t\tPrinting page: "+pageIndex);
				int numPages = fTotalSize / (int)pageFormat.getImageableHeight();
				if(fTotalSize % (int)pageFormat.getImageableHeight() != 0)
					numPages++;
				
				if(pageIndex >= numPages)
					return Printable.NO_SUCH_PAGE;
				
				int choiceIndex = 0;
				int totalSize = 0;
				while(pageIndex != 0){
					totalSize += choiceToImage.get(choices.get(choiceIndex)).getHeight(null);
					
					if(totalSize > pageFormat.getImageableHeight()){
						totalSize = 0;
						choiceIndex--;
						pageIndex--;
					}
					
					choiceIndex++;
				}
				
				totalSize = 0;
				while(totalSize < pageFormat.getImageableHeight() && choiceIndex < choices.size()){
					Image img = choiceToImage.get(choices.get(choiceIndex));
					
					graphics.drawImage(img,
							(int)pageFormat.getImageableX(),
							totalSize,
							null);
					
					totalSize += img.getHeight(null);
					choiceIndex++;
				}
				
				return Printable.PAGE_EXISTS;
			}
			
		};
		
		System.out.println("Ready to print");
		Driver.printOnVVPAT(constants, printedBallot);
	}
    
    /**
	 * Prints onto the attached VVPAT printer, if possible.
	 * @param print - the Printable to print.
	 */
	public static void printOnVVPAT(IAuditoriumParams constants, Printable toPrint){
		System.out.println("printOnVVPAT");
		
		//VVPAT not ready
		if(constants.getPrinterForVVPAT().equals("")) return;
		
		System.out.println("\tLooking up printer...");
		PrintService[] printers = PrinterJob.lookupPrintServices();
		
		PrintService vvpat = null;
		
		for(PrintService printer : printers){
			PrinterName name = printer.getAttribute(PrinterName.class);
			if(name.getValue().equals(constants.getPrinterForVVPAT())){
				vvpat = printer;
				break;
			}//if
		}//for
		
		if(vvpat == null){
			System.err.println("VVPAT is configured, but not detected as ready.");
			return;
		}
		
		System.out.println("\tCreating job");
		PrinterJob job = PrinterJob.getPrinterJob();
		
		try {
			job.setPrintService(vvpat);
		} catch (PrinterException e) {
			System.err.println("VVPAT printing failed: "+e.getMessage());
			return;
		}
		
		Paper paper = new Paper();
		paper.setSize(constants.getPaperWidthForVVPAT(), constants.getPaperHeightForVVPAT());
		
		int imageableWidth = constants.getPrintableWidthForVVPAT();
		int imageableHeight = constants.getPrintableHeightForVVPAT();
		
		int leftInset = (constants.getPaperWidthForVVPAT() - constants.getPrintableWidthForVVPAT()) / 2;
		int topInset = (constants.getPaperHeightForVVPAT() - constants.getPrintableHeightForVVPAT()) / 2;
		
		paper.setImageableArea(leftInset, topInset, imageableWidth, imageableHeight);
		PageFormat pageFormat = new PageFormat();
		pageFormat.setPaper(paper);
		
		job.setPrintable(toPrint, pageFormat);
		
		try {
			job.print();
		} catch (PrinterException e) {
			System.err.println("VVPAT printing failed: "+e.getMessage());
			return;
		}
	}
    
	public static void unzip(String src, String dest) throws IOException {
		if(!(new File(dest)).exists()){
			(new File(dest)).mkdirs();
		}//if
		
		ZipFile zipFile = new ZipFile(src);
		Enumeration<? extends ZipEntry> entries = zipFile.entries();
		byte[] buf = new byte[1024];
		int len;
		
		while (entries.hasMoreElements()) {
			ZipEntry entry = (ZipEntry) entries.nextElement();

			if (entry.isDirectory()) {
				//Create the directory using the proper seperator for this platform
				File newDir = new File(dest, entry.getName().replace('/', File.separatorChar));
				newDir.mkdirs();
				
			} else {
				InputStream in = zipFile.getInputStream(entry);
				
				//Create the file path, using the proper seperator char
				File outFile = new File(dest, entry.getName().replace('/', File.separatorChar));
				
				OutputStream out = new BufferedOutputStream(
						new FileOutputStream(outFile));
				while((len = in.read(buf)) >= 0)
				      out.write(buf, 0, len);
				in.close();
				
				out.flush();
				out.close();
			}
		}

		zipFile.close();
	}
	
	public static void deleteRecursivelyOnExit(String dir) {
		Stack<File> dirStack = new Stack<File>();
        dirStack.add( new File(dir) );
        while (!dirStack.isEmpty()) {
            File file = dirStack.pop();
            file.deleteOnExit();
            File[] children = file.listFiles();
            for (File f : children) {
                if (f.isDirectory())
                    dirStack.add( f );
                else
                    f.deleteOnExit();
            }
            if (dirStack.size() > 100)
                return;
        }
	}
}
