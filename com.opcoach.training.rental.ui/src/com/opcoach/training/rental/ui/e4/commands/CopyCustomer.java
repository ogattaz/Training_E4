package com.opcoach.training.rental.ui.e4.commands;

import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.CanExecute;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.RTFTransfer;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.opcoach.training.rental.Customer;
import com.opcoach.training.rental.ui.RentalUIConstants;

public class CopyCustomer implements RentalUIConstants {

	/**
	 * @param aSelection
	 */
	@CanExecute
	public boolean canExecute(@Named(IServiceConstants.ACTIVE_SELECTION) Object aSelection) {
		return aSelection instanceof Customer;
	}

	/**
	 * @param aCutomer
	 * @param aShell
	 */
	@Execute
	public void execute(@Named(IServiceConstants.ACTIVE_SELECTION) Customer aCutomer, Shell aShell,
			IEventBroker aBroker) {

		MessageDialog.openInformation(aShell, "Copy Client", "Copying this customer : " + (aCutomer.getDisplayName()));

		Clipboard clipboard = new Clipboard(Display.getCurrent());

		String textData = aCutomer.getDisplayName();

		String rtfData = "{\\rtf1\\b\\i " + textData + "}";
		Transfer[] transfers = new Transfer[] { TextTransfer.getInstance(), RTFTransfer.getInstance() };
		Object[] data = new Object[] { textData, rtfData };
		clipboard.setContents(data, transfers);
		clipboard.dispose();

		aBroker.send(RentalUIConstants.RENTAL_EVENT_TOPIC_COPY_CUSTOMER, aCutomer);

	}
}
