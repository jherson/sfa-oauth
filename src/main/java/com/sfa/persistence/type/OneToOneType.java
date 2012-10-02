package com.sfa.persistence.type;

public class OneToOneType {

	private String relationshipName;
	private EntityType entityType;
	private String referenceColumnName;
	
	public String getRelationshipName() {
		return relationshipName;
	}
	
	public void setRelationshipName(String relationshipName) {
		this.relationshipName = relationshipName;
	}
	
	public EntityType getEntityType() {
		return entityType;
	}
	
	public void setEntityType(EntityType entityType) {
		this.entityType = entityType;
	}
	
	public String getReferenceColumnName() {
		return referenceColumnName;
	}
	
	public void setReferenceColumnName(String referenceColumnName) {
		this.referenceColumnName = referenceColumnName;
	}
}