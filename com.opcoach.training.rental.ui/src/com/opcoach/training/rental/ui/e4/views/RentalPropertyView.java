package com.opcoach.training.rental.ui.e4.views;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.UpdateValueStrategy;
import org.eclipse.core.databinding.beans.PojoProperties;
import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.internal.databinding.conversion.DateToStringConverter;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.emf.databinding.EMFObservables;
import org.eclipse.emf.databinding.EMFProperties;
import org.eclipse.emf.databinding.FeaturePath;
import org.eclipse.jface.databinding.swt.WidgetProperties;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSource;
import org.eclipse.swt.dnd.DragSourceAdapter;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;

import com.opcoach.training.rental.Rental;
import com.opcoach.training.rental.RentalAgency;
import com.opcoach.training.rental.RentalPackage.Literals;
import com.opcoach.training.rental.ui.Messages;
import com.opcoach.training.rental.ui.RentalUIActivator;
import com.opcoach.training.rental.ui.RentalUIConstants;

/**
 * @author ogattaz
 */
public class RentalPropertyView {
	public static final String VIEW_ID = "com.opcoach.rental.ui.views.rentalView"; //$NON-NLS-1$
	private Rental currentRental;

	private Label customerNameLabel;
	private Label customerTitle;
	private Label endDateLabel;
	private DataBindingContext m_bindingContext;

	// injected agency
	@Inject
	private RentalAgency pAgency;
	private Label rentedObjectLabel;

	private Label startDateLabel;

	/**
	 * 
	 */
	public RentalPropertyView() {
		super();
	}

	/**
	 * @param parent
	 * @param aAgency
	 *            injected agency
	 */
	@PostConstruct
	public void createPartControl(Composite parent) {
		parent.setLayout(new GridLayout(1, false));

		Group infoGroup = new Group(parent, SWT.NONE);
		infoGroup.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		infoGroup.setText(Messages.RentalPropertyView_Information);
		infoGroup.setLayout(new GridLayout(2, false));

		rentedObjectLabel = new Label(infoGroup, SWT.BORDER);
		GridData gd = new GridData();
		gd.horizontalSpan = 2;
		gd.horizontalAlignment = SWT.FILL;
		rentedObjectLabel.setLayoutData(gd);

		DragSource ds = new DragSource(rentedObjectLabel, DND.DROP_COPY);
		ds.setTransfer(new Transfer[] { TextTransfer.getInstance() });
		ds.addDragListener(new DragSourceAdapter() {
			@Override
			public void dragSetData(DragSourceEvent event) {
				if (TextTransfer.getInstance().isSupportedType(event.dataType)) {
					event.data = rentedObjectLabel.getText();
				}
			}

			@Override
			public void dragStart(DragSourceEvent event) {
				event.image = RentalUIActivator.getDefault().getImageRegistry().get(RentalUIConstants.IMG_AGENCY);
			}

		});

		customerTitle = new Label(infoGroup, SWT.NONE);
		customerTitle.setText(Messages.RentalPropertyView_RentedBy);
		customerNameLabel = new Label(infoGroup, SWT.NONE);

		Group dateGroup = new Group(parent, SWT.NONE);
		dateGroup.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		dateGroup.setText(Messages.RentalPropertyView_RentalDateTitle);
		dateGroup.setLayout(new GridLayout(2, false));

		Label startDateTitle = new Label(dateGroup, SWT.NONE);
		startDateTitle.setText(Messages.RentalPropertyView_From);
		startDateLabel = new Label(dateGroup, SWT.NONE);

		Label endDateTitle = new Label(dateGroup, SWT.NONE);
		endDateTitle.setText(Messages.RentalPropertyView_To);
		endDateLabel = new Label(dateGroup, SWT.NONE);

		// Fill with sample
		setRental(pAgency.getRentals().get(0));
		m_bindingContext = initDataBindings();

		// Initialize binding
		// m_bindingContext = initDataBindings();

	}

	// E34 portage selection listener
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.part.WorkbenchPart#dispose()
	 */
	// @Override
	// public void dispose() {
	// getSite().getPage().removeSelectionListener(this);
	// super.dispose();
	// }
	//
	// @Override
	// public void init(IViewSite site) throws PartInitException {
	// super.init(site);
	// site.getPage().addSelectionListener(this);
	// }

	/**
	 * @return
	 */
	protected DataBindingContext initDataBindings() {
		DataBindingContext bindingContext = new DataBindingContext();

		//
		IObservableValue<?> observeTextRentedObjectLabelObserveWidget = WidgetProperties.text()
				.observe(rentedObjectLabel);
		IObservableValue<?> currentRentalNameObserveValue = EMFProperties
				.value(FeaturePath.fromList(Literals.RENTAL__RENTED_OBJECT, Literals.RENTAL_OBJECT__NAME))
				.observe(currentRental);
		bindingContext.bindValue(observeTextRentedObjectLabelObserveWidget, currentRentalNameObserveValue, null, null);
		//
		IObservableValue<?> observeTextStartDateLabelObserveWidget = WidgetProperties.text().observe(startDateLabel);
		IObservableValue<?> startDateCurrentRentalObserveValue = PojoProperties.value("startDate")
				.observe(currentRental);
		UpdateValueStrategy strategy = new UpdateValueStrategy();
		strategy.setConverter(new DateToStringConverter());
		bindingContext.bindValue(observeTextStartDateLabelObserveWidget, startDateCurrentRentalObserveValue, null,
				strategy);
		//
		IObservableValue<?> observeTextEndDateLabelObserveWidget = WidgetProperties.text().observe(endDateLabel);
		IObservableValue<?> currentRentalEndDateObserveValue = EMFObservables.observeValue(currentRental,
				Literals.RENTAL__END_DATE);
		UpdateValueStrategy strategy_1 = new UpdateValueStrategy();
		strategy_1.setConverter(new DateToStringConverter());
		bindingContext.bindValue(observeTextEndDateLabelObserveWidget, currentRentalEndDateObserveValue, null,
				strategy_1);
		//
		IObservableValue<?> observeTextCustomerNameLabelObserveWidget = WidgetProperties.text()
				.observe(customerNameLabel);
		IObservableValue<?> customerdisplayNameCurrentRentalObserveValue = PojoProperties.value("customer.displayName")
				.observe(currentRental);
		bindingContext.bindValue(observeTextCustomerNameLabelObserveWidget,
				customerdisplayNameCurrentRentalObserveValue, null, null);
		//
		return bindingContext;
	}

	// E34

	// @Override
	// public void selectionChanged(IWorkbenchPart part, ISelection selection) {
	// if (selection.isEmpty())
	// return;
	//
	// if (selection instanceof IStructuredSelection) {
	// Object sel = ((IStructuredSelection) selection).getFirstElement();
	//
	// // La selection courante est elle un Rental ou adaptable en Rental ?
	// Rental r = Platform.getAdapterManager().getAdapter(sel, Rental.class);
	// setRental(r);
	//
	// }
	//
	// }

	/**
	 * 
	 */
	@Focus
	public void setFocus() {
		// E34
	}

	/**
	 * @param r
	 */
	public void setRental(Rental r) {
		currentRental = r;

		if (m_bindingContext != null) {
			m_bindingContext.dispose();
			m_bindingContext = null;
		}

		if (r == null) {
			rentedObjectLabel.setText(" ");
			customerNameLabel.setText(" ");
			startDateLabel.setText(" ");
			endDateLabel.setText(" ");
		} else {

			m_bindingContext = initDataBindings();

		}

	}
}
