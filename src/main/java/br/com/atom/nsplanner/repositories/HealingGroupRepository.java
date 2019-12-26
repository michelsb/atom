package br.com.atom.nsplanner.repositories;

import java.util.ArrayList;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;

import br.com.atom.common.owlmanager.OntologyManager;
import br.com.atom.nsplanner.dtos.HealingCriteriaDto;
import br.com.atom.nsplanner.dtos.HealingGroupDto;
import br.com.atom.nsplanner.dtos.HealingPolicyDto;
import br.com.atom.nsplanner.dtos.VNFDMemberDto;
import br.com.atom.nsplanner.util.NamedClasses;
import br.com.atom.nsplanner.util.NamedDataProp;
import br.com.atom.nsplanner.util.NamedObjectProp;

public class HealingGroupRepository {
	
	protected OntologyManager ontomanager;
	protected HealingPolicyRepository hprDB;
	protected HealingCriteriaRepository hcrDB;

	public HealingGroupRepository(OntologyManager ontomanager) {
		this.ontomanager = ontomanager;
		this.hprDB = new HealingPolicyRepository(ontomanager);
		this.hcrDB = new HealingCriteriaRepository(ontomanager);
	}

	public ArrayList<HealingGroupDto> getHealingGroups() {
		
		ArrayList<HealingGroupDto> listHgs = new ArrayList<HealingGroupDto>();

		/*ReasonerFactory reasonerFactory = new ReasonerFactory();
		Configuration configuration = new Configuration();
		configuration.throwInconsistentOntologyException = false;
		OWLReasoner reasoner = reasonerFactory.createReasoner(ontomanager.getOntology(), configuration);
		reasoner.precomputeInferences();*/
		
		Set<OWLNamedIndividual> owlHgInds = this.ontomanager.getClassIndividualsWithoutReasoner(NamedClasses.HEALINGGROUP);
		
		for (OWLNamedIndividual owlHgInd : owlHgInds) {
			String owlHgIndName = owlHgInd.getIRI().getShortForm();
			
			HealingGroupDto hgDto = new HealingGroupDto();
			hgDto.setName(owlHgIndName);
			
			ArrayList<VNFDMemberDto> listVNFMembers = new ArrayList<VNFDMemberDto>();
			Set<OWLLiteral> vnfMembemIndexes = ontomanager.getDpValuesPerIndividualWithoutReasoner(owlHgIndName,
					NamedDataProp.HASMEMBERVNFINDEXREF);
			for (OWLLiteral vnfMembemIndex : vnfMembemIndexes) {
				VNFDMemberDto vnfdMemberDto = new VNFDMemberDto();
				vnfdMemberDto.setMemberVnfIndexRef(vnfMembemIndex.getLiteral());
				vnfdMemberDto.setCount(1);
				listVNFMembers.add(vnfdMemberDto);
			}
			hgDto.setVnfdMembers(listVNFMembers);

			HealingPolicyDto hpDto = this.hprDB.getDefaultHealingPolicyData();
			hpDto.setName("HP-"+owlHgIndName);
			
			ArrayList<HealingCriteriaDto> listHcs = new ArrayList<HealingCriteriaDto>();
			Set<OWLNamedIndividual> owlHcInds = ontomanager.getOpValuesPerIndividualWithoutReasoner(owlHgIndName,
					NamedObjectProp.HASHEALINGCRITERIA);
		
			for (OWLNamedIndividual owlHcInd : owlHcInds) {
				String hcIndName = owlHcInd.getIRI().getShortForm();
				HealingCriteriaDto hcDto = this.hcrDB.getHealingCriteria(hcIndName);
				listHcs.add(hcDto);
			}

			hpDto.setHealingCriterias(listHcs);
			hgDto.setHealingPolicy(hpDto);

			listHgs.add(hgDto);
		
		}
		
		return listHgs;
		
	}
	
}
