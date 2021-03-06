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

package votebox.middle.view.widget;

/**
 * This strategy encapsulates the behavior expected with radio buttons. When any
 * button wants to be selected, it is allowed, but all other buttons in its
 * group are deselected first.
 * 
 * @author Kyle
 * 
 */
public class RadioButton extends AToggleButtonGroupStrategy {

	/**
	 * Singleton Design Pattern
	 */
	public static final RadioButton Singleton = new RadioButton();
	
	/**
	 * Singleton Design Pattern
	 */
	private RadioButton(){
	}
	
	/**
	 * @see votebox.middle.view.widget.AToggleButtonGroupStrategy#select(votebox.middle.view.widget.ToggleButton)
	 */
	public void select(ToggleButton element) {
		// Deselect everyone else
		for (ToggleButton button : element.getGroup().getButtons()) {
			if (!button.equals(element)) {
				button.makeDeselected();
			}
		}
		// Select the new guy.
		element.makeSelected();
	}

	/**
	 * @see votebox.middle.view.widget.AToggleButtonGroupStrategy#deselect(votebox.middle.view.widget.ToggleButton)
	 */
	public void deselect(ToggleButton element) {
		// NO-OP
	}
}