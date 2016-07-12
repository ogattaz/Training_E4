
package com.opcoach.training.rental.ui;

import javax.annotation.PostConstruct;

import org.eclipse.e4.core.contexts.IEclipseContext;

import com.opcoach.training.rental.RentalAgency;
import com.opcoach.training.rental.helpers.RentalAgencyGenerator;

/**
 * @author ogattaz
 *
 */
public class RentalAddOn {

	/**
	 * @param aContext
	 */
	@PostConstruct
	public void init(final IEclipseContext aContext) {

		aContext.set(RentalAgency.class, RentalAgencyGenerator.createSampleAgency());
	}

}
