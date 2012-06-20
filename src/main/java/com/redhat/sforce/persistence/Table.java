package com.redhat.sforce.persistence;

public @interface Table {

	String name() default "";
}