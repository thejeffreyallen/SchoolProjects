/**
 * DFAState implementation
 * 
 * @Author Jeff Allen
 * 
 * class - CS361
 * Project 1 
 */

package fa.dfa;
import java.util.HashMap;

import fa.State;


public class DFAState extends State{
	
	private boolean isStart; // identifier
	private boolean isFinal; // identifier
	private HashMap<Character ,DFAState> map; // Map of transitions on a character for the given state.
	
	/**
	 * Default constructor
	 * 
	 * @param name - Name of the state, i.e. q0, q1, etc..
	 */
	public DFAState(String name) {
		super.name = name;
		isFinal = false;
		isStart = false;
		this.map = new HashMap<Character ,DFAState>();
	}
	
	/**
	 * Secondary constructor
	 * 
	 * @param name - Name of the state, i.e. q0, q1, etc..
	 * @param s - is the state the start state?
	 * @param f - is the state a final state?
	 */
	public DFAState(String name, boolean s, boolean f) {
		this.name = name;
		this.isStart = s;
		this.isFinal = f;
		this.map = new HashMap<Character ,DFAState>();
	}
	
	/**
	 * isFinal method
	 * 
	 * @return - whether the state is final or not.
	 */
	public boolean isFinal() {
		return this.isFinal;
	}
	
	/**
	 * isStart method
	 * 
	 * @return - whether the state is the start state or not.
	 */
	public boolean isStart() {
		return this.isStart;
	}
	
	/**
	 * addTransition method - A helper method that is called from the DFA.java file in order to add a transition to this state
	 * 
	 * @param onSymb - The alphabet symbol to transition on 
	 * @param toState - The state that the symbol leads to. Can be the same state.
	 */
	public void addTransition(char onSymb, DFAState toState) {
		map.put(onSymb, toState);
	}
	
	/**
	 * getToState method - A helper method that is called from the DFA.java file in order to move from this state to the next state. Can be the same state.
	 * 
	 * @param onSymb - The alphabet symbol to transition on
	 * @return - The state that you end up on after following the transition
	 */
	public DFAState getToState(char onSymb) {
		DFAState temp = map.get(onSymb);
		return temp;
	}
	
}
