package net.prokhyon.modularfuzzy.fuzzyAutomaton.model.descriptor;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import net.prokhyon.modularfuzzy.common.modelDescriptor.FuzzyDescriptorModelBase;

import java.util.List;

@XStreamAlias("transition")
public class FuzzyTransition extends FuzzyDescriptorModelBase {

	@XStreamAlias("fromState")
	@XStreamAsAttribute
	private String fromState;

	@XStreamAlias("toState")
	@XStreamAsAttribute
	private String toState;

	@XStreamImplicit
	@XStreamAlias("costs")
	private List<Double> costs;

	public FuzzyTransition(String label, String description, String fromState, String toState,
						   List<Double> costs) {
		super(label, description);
		this.fromState = fromState;
		this.toState = toState;
		this.costs = costs;
	}

	public String getFromState() {
		return fromState;
	}

	public String getToState() {
		return toState;
	}

	@Override
	public String toString() {
		return "FuzzyTransition [fromState=" + fromState + ", toState=" + toState + "]";
	}

}
