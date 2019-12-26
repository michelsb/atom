package br.com.atom.nsplanner.repositories;

import java.util.ArrayList;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;

import br.com.atom.common.owlmanager.OntologyManager;
import br.com.atom.nsplanner.dtos.ScalingCriteriaDto;
import br.com.atom.nsplanner.dtos.ScalingGroupDto;
import br.com.atom.nsplanner.dtos.ScalingPolicyDto;
import br.com.atom.nsplanner.dtos.VNFDMemberDto;
import br.com.atom.nsplanner.util.NamedClasses;
import br.com.atom.nsplanner.util.NamedDataProp;
import br.com.atom.nsplanner.util.NamedObjectProp;
import br.com.atom.nsplanner.util.OntoPlannerUtil;

public class ScalingGroupRepository {

	protected OntologyManager ontomanager;
	protected ScalingPolicyRepository sprDB;
	protected ScalingCriteriaRepository scrDB;

	public ScalingGroupRepository(OntologyManager ontomanager) {
		this.ontomanager = ontomanager;
		this.sprDB = new ScalingPolicyRepository(ontomanager);
		this.scrDB = new ScalingCriteriaRepository(ontomanager);
	}

	public ArrayList<ScalingGroupDto> getScalingGroups() {

		ArrayList<ScalingGroupDto> listSgs = new ArrayList<ScalingGroupDto>();

		/*
		 * ReasonerFactory reasonerFactory = new ReasonerFactory(); Configuration
		 * configuration = new Configuration();
		 * configuration.throwInconsistentOntologyException = false; OWLReasoner
		 * reasoner = reasonerFactory.createReasoner(ontomanager.getOntology(),
		 * configuration); reasoner.precomputeInferences();
		 */

		Set<OWLNamedIndividual> owlSgInds = ontomanager.getClassIndividualsWithoutReasoner(NamedClasses.SCALINGGROUP);

		for (OWLNamedIndividual owlSgInd : owlSgInds) {
			String owlSgIndName = owlSgInd.getIRI().getShortForm();

			if (!owlSgIndName.equals(OntoPlannerUtil.SCALINGGROUP_TEMPLATE_NAME)) {
				ScalingGroupDto sgDto = new ScalingGroupDto();
				sgDto.setName(owlSgIndName);

				Set<OWLLiteral> minInstanceCount = ontomanager.getDpValuesPerIndividualWithoutReasoner(
						OntoPlannerUtil.SCALINGGROUP_TEMPLATE_NAME, NamedDataProp.HASMININSTANCECOUNT);
				for (OWLLiteral value : minInstanceCount) {
					sgDto.setMinInstanceCount(value.parseInteger());
					break;
				}

				Set<OWLLiteral> maxInstanceCount = ontomanager.getDpValuesPerIndividualWithoutReasoner(
						OntoPlannerUtil.SCALINGGROUP_TEMPLATE_NAME, NamedDataProp.HASMAXINSTANCECOUNT);
				for (OWLLiteral value : maxInstanceCount) {
					sgDto.setMaxInstanceCount(value.parseInteger());
					break;
				}

				int count = 1;
				Set<OWLLiteral> counts = ontomanager.getDpValuesPerIndividualWithoutReasoner(
						OntoPlannerUtil.SCALINGGROUP_TEMPLATE_NAME, NamedDataProp.HASCOUNT);
				for (OWLLiteral value : counts) {
					count = value.parseInteger();
					break;
				}

				ArrayList<VNFDMemberDto> listVNFMembers = new ArrayList<VNFDMemberDto>();
				Set<OWLLiteral> vnfMembemIndexes = ontomanager.getDpValuesPerIndividualWithoutReasoner(owlSgIndName,
						NamedDataProp.HASMEMBERVNFINDEXREF);

				for (OWLLiteral vnfMembemIndex : vnfMembemIndexes) {
					VNFDMemberDto vnfdMemberDto = new VNFDMemberDto();
					vnfdMemberDto.setMemberVnfIndexRef(vnfMembemIndex.getLiteral());
					vnfdMemberDto.setCount(count);
					listVNFMembers.add(vnfdMemberDto);
				}
				sgDto.setVnfdMembers(listVNFMembers);

				ScalingPolicyDto spDto = this.sprDB.getDefaultScalingPolicyData();
				spDto.setName("SP-" + owlSgIndName);

				ArrayList<ScalingCriteriaDto> listScs = new ArrayList<ScalingCriteriaDto>();
				Set<OWLNamedIndividual> owlScInds = ontomanager.getOpValuesPerIndividualWithoutReasoner(owlSgIndName,
						NamedObjectProp.HASSCALINGCRITERIA);
				System.out.println(" ");
				for (OWLNamedIndividual owlScInd : owlScInds) {
					String scIndName = owlScInd.getIRI().getShortForm();
					ScalingCriteriaDto scDto = this.scrDB.getScalingCriteria(scIndName);
					listScs.add(scDto);
				}

				spDto.setScalingCriterias(listScs);
				sgDto.setScalingPolicy(spDto);

				listSgs.add(sgDto);
			}
		}

		return listSgs;

	}

	/*
	 * public ScalingGroup getScalingPolicyData(OWLReasoner reasoner) {
	 * 
	 * 
	 * 
	 * Set<OWLLiteral> values1 =
	 * ontomanager.getDpValuesPerIndividual(OntoPlannerUtil.
	 * SCALINGPOLICY_TEMPLATE_NAME, NamedDataProp.HASSCALINGTYPE, reasoner, true);
	 * for (OWLLiteral value : values1) { sp.setScalingType(value.getLiteral());
	 * break; } Set<OWLLiteral> values2 =
	 * ontomanager.getDpValuesPerIndividual(OntoPlannerUtil.
	 * SCALINGPOLICY_TEMPLATE_NAME, NamedDataProp.HASSCALEINOPERATIONTYPE, reasoner,
	 * false); for (OWLLiteral value : values2) {
	 * sp.setScaleInOperationType(value.getLiteral()); break; } Set<OWLLiteral>
	 * values3 = ontomanager.getDpValuesPerIndividual(OntoPlannerUtil.
	 * SCALINGPOLICY_TEMPLATE_NAME, NamedDataProp.HASSCALEOUTOPERATIONTYPE,
	 * reasoner, false); for (OWLLiteral value : values3) {
	 * sp.setScaleOutOperationType(value.getLiteral()); break; } Set<OWLLiteral>
	 * values4 = ontomanager.getDpValuesPerIndividual(OntoPlannerUtil.
	 * SCALINGPOLICY_TEMPLATE_NAME, NamedDataProp.HASTHRESHOLDTIME, reasoner,
	 * false); for (OWLLiteral value : values4) {
	 * sp.setThresholdTime(value.parseInteger()); break; } Set<OWLLiteral> values5 =
	 * ontomanager.getDpValuesPerIndividual(OntoPlannerUtil.
	 * SCALINGPOLICY_TEMPLATE_NAME, NamedDataProp.HASCOOLDOWNTIME, reasoner, false);
	 * for (OWLLiteral value : values5) { sp.setCoolDownTime(value.parseInteger());
	 * break; } return sp; }
	 */

}
