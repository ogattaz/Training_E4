package com.opcoach.training.rental.ui.views;

import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.dnd.DragSourceAdapter;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.ImageTransfer;
import org.eclipse.swt.dnd.RTFTransfer;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.URLTransfer;

import com.opcoach.training.rental.Customer;
import com.opcoach.training.rental.ui.RentalUIConstants;

/**
 * @author ogattaz
 *
 */
final public class AgencyTreeDragSourceListener extends DragSourceAdapter {

	private final ImageRegistry pImageRegistry;
	private ISelectionProvider selProvider = null;

	/**
	 * @param selProvider
	 * @param aImageRegistry
	 */
	public AgencyTreeDragSourceListener(ISelectionProvider selProvider, ImageRegistry aImageRegistry) {
		super();
		this.selProvider = selProvider;
		pImageRegistry = aImageRegistry;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.swt.dnd.DragSourceAdapter#dragSetData(org.eclipse.swt.dnd.
	 * DragSourceEvent)
	 */
	@Override
	public void dragSetData(DragSourceEvent event) {
		ISelection sel = selProvider.getSelection();
		Object selectedObject = (sel instanceof IStructuredSelection) ? ((IStructuredSelection) sel).getFirstElement()
				: null;
		if (selectedObject == null)
			return;

		if (ImageTransfer.getInstance().isSupportedType(event.dataType)) {
			if (selectedObject instanceof Customer)
				event.data = pImageRegistry.get(RentalUIConstants.IMG_CUSTOMER);
			else
				event.data = null;
		}
		if (RTFTransfer.getInstance().isSupportedType(event.dataType)) {
			if (selectedObject instanceof Customer)
				event.data = "{\\rtf1\\b\\i " + ((Customer) selectedObject).getDisplayName() + "}";
			else
				event.data = "{\\rtf1 " + selectedObject.toString() + "}";
		} else if (URLTransfer.getInstance().isSupportedType(event.dataType)) {
			if (selectedObject instanceof Customer) {
				Customer c = (Customer) selectedObject;
				event.data = "http://www.google.fr/search?q=" + c.getDisplayName();
			} else
				event.data = "http://www.yahoo.fr";

		} else if (TextTransfer.getInstance().isSupportedType(event.dataType)) {
			if (selectedObject instanceof Customer) {
				Customer c = (Customer) selectedObject;
				event.data = "Customer : " + c.getDisplayName();
			} else
				event.data = selectedObject.toString();

		}

	}
}