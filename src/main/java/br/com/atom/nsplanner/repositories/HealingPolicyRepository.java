package br.com.atom.nsplanner.repositories;

import java.util.Set;

import org.semanticweb.owlapi.model.OWLLiteral;

import br.com.atom.common.owlmanager.OntologyManager;
import br.com.atom.nsplanner.dtos.HealingPolicyDto;
import br.com.atom.nsplanner.util.NamedDataProp;
import br.com.atom.nsplanner.util.OntoPlannerUtil;

public class HealingPolicyRepository {

	protected OntologyManager ontomanager;

	public HealingPolicyRepository(OntologyManager ontomanager) {
		this.ontomanager = ontomanager;
	}

	public HealingPolicyDto getDefaultHealingPolicyData() {

		HealingPolicyDto hp = new HealingPolicyDto();

		hp.setEnabled(true);

		Set<OWLLiteral> values1 = ontomanager.getDpValuesPerIndividualWithoutReasoner(OntoPlannerUtil.HEALINGPOLICY_TEMPLATE_NAME,
				NamedDataProp.HASHEALINGTYPE);
		for (OWLLiteral value : values1) {
			hp.setHealingType(value.getLiteral());
			break;
		}

		Set<OWLLiteral> values2 = ontomanager.getDpValuesPerIndividualWithoutReasoner(OntoPlannerUtil.HEALINGPOLICY_TEMPLATE_NAME,
				NamedDataProp.HASHEALOPERATIONTYPE);
		for (OWLLiteral value : values2) {
			hp.setHealOperationType(value.getLiteral());
			break;
		}

		Set<OWLLiteral> values3 = ontomanager.getDpValuesPerIndividualWithoutReasoner(OntoPlannerUtil.HEALINGPOLICY_TEMPLATE_NAME,
				NamedDataProp.HASTHRESHOLDTIME);
		for (OWLLiteral value : values3) {
			hp.setThresholdTime(value.parseInteger());
			break;
		}

		Set<OWLLiteral> values4 = ontomanager.getDpValuesPerIndividualWithoutReasoner(OntoPlannerUtil.HEALINGPOLICY_TEMPLATE_NAME,
				NamedDataProp.HASCOOLDOWNTIME);
		for (OWLLiteral value : values4) {
			hp.setCoolDownTime(value.parseInteger());
			break;
		}
		return hp;

	}

}
