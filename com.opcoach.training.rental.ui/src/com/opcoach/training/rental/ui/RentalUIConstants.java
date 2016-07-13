package com.opcoach.training.rental.ui;

/**
 * Constant definitions for plug-in preferences, keys for colors, and to
 * identify objects nature.
 */
public interface RentalUIConstants {

	// Nodes for providers
	public static final String CUSTOMERS_NODE = "Clients";
	// Constants for font values
	public static final String FONT_CUSTOMER = "customerFont";
	public static final String FONT_RENTAL_OBJECT = "rentalObjectKey";
	// Constants to manage object images in registry. Constant values are path
	// to icons
	public static final String IMG_AGENCY = "icons/Agency.png";
	public static final String IMG_COLLAPSE_ALL = "icons/collapseall.gif";
	public static final String IMG_CUSTOMER = "icons/Customers.png";

	public static final String IMG_EXPAND_ALL = "icons/expandall.gif";
	public static final String IMG_RENTAL = "icons/Rentals.png";
	public static final String IMG_RENTAL_OBJECT = "icons/RentalObjects.png";
	public static final String OBJECTS_NODE = "Objets à louer";
	public static final String PREF_CUSTOMER_COLOR = "customerColor";

	// Constants for preference values
	public static final String PREF_DISPLAY_COUNT = "displayCounter";
	public static final String PREF_PALETTE = "palette";
	public static final String PREF_RENTAL_COLOR = "rentalColor";

	public static final String PREF_RENTAL_OBJECT_COLOR = "rentalObjectColor";

	// E3 => E4
	static String RENTAL_EVENT_TOPIC_COPY_CUSTOMER = "rental/copy/customer";

	// E3 => E4 gestion imageregistry par bundle
	static String RENTAL_IMAGE_REGISTRY_ID = "RENTAL_IMAGE_REGISTRY_ID";

	public static final String RENTALS_NODE = "Locations";

}
