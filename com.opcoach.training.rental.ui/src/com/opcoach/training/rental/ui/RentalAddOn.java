
package com.opcoach.training.rental.ui;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.UIEventTopic;

import com.opcoach.training.rental.Customer;
import com.opcoach.training.rental.RentalAgency;
import com.opcoach.training.rental.helpers.RentalAgencyGenerator;

/**
 * @author ogattaz
 *
 */
public class RentalAddOn implements RentalUIConstants {

	/**
	 * @param aContext
	 */
	@PostConstruct
	public void init(final IEclipseContext aContext) {

		aContext.set(RentalAgency.class, RentalAgencyGenerator.createSampleAgency());
	}

	@Inject
	@Optional
	public void listenCopyCustom(@UIEventTopic(RENTAL_EVENT_TOPIC_COPY_CUSTOMER) Customer aCustomer) {

		System.out.println(String.format("Topic=[%s] aCustomer=[%s]", RENTAL_EVENT_TOPIC_COPY_CUSTOMER, aCustomer));
	}

}
