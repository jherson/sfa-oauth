package com.sfa.persistence.type;

public class OneToManyType {

	private String relationshipName;
	private EntityType entityType;
	
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
}