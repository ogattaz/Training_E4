package com.opcoach.training.rental.ui.e4.views;

import java.util.ArrayList;
import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSource;
import org.eclipse.swt.dnd.RTFTransfer;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.dnd.URLTransfer;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import com.opcoach.training.rental.RentalAgency;
import com.opcoach.training.rental.helpers.RentalAgencyGenerator;
import com.opcoach.training.rental.ui.RentalUIActivator;
import com.opcoach.training.rental.ui.RentalUIConstants;
import com.opcoach.training.rental.ui.views.AgencyTreeDragSourceListener;
import com.opcoach.training.rental.ui.views.RentalProvider;

/**
 * @author ogattaz
 *
 */
public class RentalAgencyView implements RentalUIConstants {
	public static final String VIEW_ID = "com.opcoach.rental.ui.rentalagencyview";

	private TreeViewer agencyViewer;

	// injected agency
	@Inject
	private RentalAgency pAgency;

	private RentalProvider provider;

	/**
	 * 
	 */
	public RentalAgencyView() {
		super();
	}

	/**
	 * @param parent
	 */
	@PostConstruct
	public void createPartControl(Composite parent) {

		parent.setLayout(new GridLayout(1, false));

		final Composite comp = new Composite(parent, SWT.NONE);
		comp.setLayout(new GridLayout(2, false));
		ImageRegistry imgReg = RentalUIActivator.getDefault().getImageRegistry();
		Button expandAll = new Button(comp, SWT.FLAT);
		expandAll.setImage(imgReg.get(IMG_EXPAND_ALL));
		expandAll.setToolTipText("Expand agency tree");
		expandAll.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}

			@Override
			public void widgetSelected(SelectionEvent e) {
				agencyViewer.expandAll();
			}
		});
		Button collapseAll = new Button(comp, SWT.FLAT);
		collapseAll.setImage(imgReg.get(IMG_COLLAPSE_ALL));
		collapseAll.setToolTipText("Collapse agency tree");
		collapseAll.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
			}

			@Override
			public void widgetSelected(SelectionEvent e) {
				agencyViewer.collapseAll();
			}
		});

		agencyViewer = new TreeViewer(parent);
		agencyViewer.getControl().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		provider = new RentalProvider();
		agencyViewer.setContentProvider(provider);
		agencyViewer.setLabelProvider(provider);

		Collection<RentalAgency> agencies = new ArrayList<>();
		// add injected agency
		agencies.add(pAgency);

		RentalAgency lyon = RentalAgencyGenerator.createSampleAgency();
		lyon.setName("Lyon");
		agencies.add(lyon);

		agencyViewer.setInput(agencies);

		// Association de la vue sur un contexte d'aide
		PlatformUI.getWorkbench().getHelpSystem().setHelp(agencyViewer.getControl(),
				"com.opcoach.training.rental.ui.rentalContext");

		// Autorise le popup sur le treeviewer
		MenuManager menuManager = new MenuManager();
		Menu menu = menuManager.createContextMenu(agencyViewer.getControl());
		agencyViewer.getControl().setMenu(menu);
		// E34 revoir la gestion du popup menu de la vue
		// getSite().registerContextMenu(menuManager, agencyViewer);

		// L'arbre est draggable
		DragSource ds = new DragSource(agencyViewer.getControl(), DND.DROP_COPY);
		Transfer[] ts = new Transfer[] { TextTransfer.getInstance(), RTFTransfer.getInstance(),
				URLTransfer.getInstance() };
		ds.setTransfer(ts);
		ds.addDragListener(new AgencyTreeDragSourceListener(agencyViewer));

		// E34 revoir la gestion du popup menu de la vue
		// getSite().setSelectionProvider(agencyViewer);

	}

	// E34 revoir la gestiond des listener
	public void dispose() {
		// RentalUIActivator.getDefault().getPreferenceStore().removePropertyChangeListener(this);
		//
		// // This treeview must remove the selection listener
		// getSite().getPage().removeSelectionListener(this);
		//
		// super.dispose();
	}

	// E34 revoir la gestiond des listener
	public void init(IViewSite site) throws PartInitException {
		// super.init(site);
		// // On s'enregistre en tant que pref listener sur le preference
		// store...
		// RentalUIActivator.getDefault().getPreferenceStore().addPropertyChangeListener(this);
		//
		// // This treeview is now selection listener to be synchronized with
		// the
		// // dashboard.
		// getSite().getPage().addSelectionListener(this);

	}

	// E34 revoir
	public void propertyChange(PropertyChangeEvent event) {
		provider.initPalette();
		agencyViewer.refresh();
	}

	// E34 revoir gestion de la sélection
	public void selectionChanged(IWorkbenchPart part, ISelection selection) {
		// Must check if this selection is coming from this part or from another
		// one.
		if (part != this)
			agencyViewer.setSelection(selection, true);

	}

	/**
	 * 
	 */
	public void setFocus() {

	}

}
