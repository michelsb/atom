package br.com.atom.nsplanner.repositories;

import java.util.Set;

import org.semanticweb.owlapi.model.OWLLiteral;

import br.com.atom.common.owlmanager.OntologyManager;
import br.com.atom.nsplanner.dtos.ScalingPolicyDto;
import br.com.atom.nsplanner.util.NamedDataProp;
import br.com.atom.nsplanner.util.OntoPlannerUtil;

public class ScalingPolicyRepository {

	protected OntologyManager ontomanager;

	public ScalingPolicyRepository(OntologyManager ontomanager) {
		this.ontomanager = ontomanager;
	}

	public ScalingPolicyDto getDefaultScalingPolicyData() {

		ScalingPolicyDto sp = new ScalingPolicyDto();

		sp.setEnabled(true);

		Set<OWLLiteral> values1 = ontomanager.getDpValuesPerIndividualWithoutReasoner(
				OntoPlannerUtil.SCALINGPOLICY_TEMPLATE_NAME, NamedDataProp.HASSCALINGTYPE);
		for (OWLLiteral value : values1) {
			sp.setScalingType(value.getLiteral());
			break;
		}
		Set<OWLLiteral> values2 = ontomanager.getDpValuesPerIndividualWithoutReasoner(
				OntoPlannerUtil.SCALINGPOLICY_TEMPLATE_NAME, NamedDataProp.HASSCALEINOPERATIONTYPE);
		for (OWLLiteral value : values2) {
			sp.setScaleInOperationType(value.getLiteral());
			break;
		}
		Set<OWLLiteral> values3 = ontomanager.getDpValuesPerIndividualWithoutReasoner(
				OntoPlannerUtil.SCALINGPOLICY_TEMPLATE_NAME, NamedDataProp.HASSCALEOUTOPERATIONTYPE);
		for (OWLLiteral value : values3) {
			sp.setScaleOutOperationType(value.getLiteral());
			break;
		}
		Set<OWLLiteral> values4 = ontomanager.getDpValuesPerIndividualWithoutReasoner(
				OntoPlannerUtil.SCALINGPOLICY_TEMPLATE_NAME, NamedDataProp.HASTHRESHOLDTIME);
		for (OWLLiteral value : values4) {
			sp.setThresholdTime(value.parseInteger());
			break;
		}
		Set<OWLLiteral> values5 = ontomanager.getDpValuesPerIndividualWithoutReasoner(
				OntoPlannerUtil.SCALINGPOLICY_TEMPLATE_NAME, NamedDataProp.HASCOOLDOWNTIME);
		for (OWLLiteral value : values5) {
			sp.setCoolDownTime(value.parseInteger());
			break;
		}
		return sp;
	}

}
