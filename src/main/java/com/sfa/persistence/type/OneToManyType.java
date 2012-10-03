package com.sfa.persistence.type;

public class OneToManyType {

	private String relationshipName;
	private EntityType entityType;
	
	public OneToManyType(String relationshipName, EntityType entityType) {
		this.relationshipName = relationshipName;
		this.entityType = entityType;
	}
	
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