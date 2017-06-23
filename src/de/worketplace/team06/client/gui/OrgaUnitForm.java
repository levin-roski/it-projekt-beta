package de.worketplace.team06.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.worketplace.team06.client.ClientsideSettings;
import de.worketplace.team06.shared.WorketplaceAdministrationAsync;
import de.worketplace.team06.shared.bo.Marketplace;

/**
 * Formular für die Darstellung und Bearbeitung der aktuellen OrgaUnit. Falls
 * keine OrgaUnit beim Initialisieren in den ClientsideSettings verfügbar ist,
 * bleibt das Formular leer, bereit für die Erstellung eines neuen Users. Im
 * Anschluss wird dann der geänderte oder hinzugefügte User den
 * ClientsideSettings hinzugefügt.
 *
 * @author Roski
 */
public class OrgaUnitForm extends Page {
	private WorketplaceAdministrationAsync worketplaceAdministration = ClientsideSettings
			.getWorketplaceAdministration();
	private Label nameLabel = new Label("Name");
	private TextBox nameInput = new TextBox();
	private Label beschreibungLabel = new Label("Beschreibung");
	private TextBox beschreibungInput = new TextBox();
	private Boolean shouldUpdate = false;
	private Marketplace toChangeMarketplace;
	private HorizontalPanel changeHeadline;
	private HorizontalPanel addHeadline;

	/**
	 * Im Konstruktor kann eine selektierter Marktplatz übergeben werden, der
	 * dann bearbeitet und gelöscht werden kann. null übergeben, falls ein neuer
	 * Marktplatz erstellt werden soll.
	 * 
	 * 
	 */
	public OrgaUnitForm(Marketplace pToChangeMarketplace, final boolean pHeadline) {
		if (pToChangeMarketplace != null) {
			shouldUpdate = true;
			this.toChangeMarketplace = pToChangeMarketplace;
		}
		if (pHeadline) {
			changeHeadline = createHeadline("Marktplatz bearbeiten", true);
			addHeadline = createHeadline("Marktplatz hinzufügen", true);
		}
	}

	/**
	 * Im Konstruktor kann eine selektierter Marktplatz übergeben werden, der
	 * dann bearbeitet und gelöscht werden kann. null übergeben, falls ein neuer
	 * Marktplatz erstellt werden soll.
	 * 
	 *
	 */
	public OrgaUnitForm(Marketplace pToChangeMarketplace, final boolean pHeadline, final Boolean pClosingHeadline) {
		this(pToChangeMarketplace, pHeadline);
		if (pClosingHeadline) {
			changeHeadline = createHeadlineWithCloseButton("Marktplatz bearbeiten", true);
			addHeadline = createHeadlineWithCloseButton("Marktplatz hinzufügen", true);
		}

		/*
		 * Grid mit 3 Zeilen und 2 Spalten für das Formular bereitstellen.
		 * Danach nötige Panels einfügen und diesem Widget hinzufügen.
		 */
		Grid form = new Grid(3, 2);
		form.setWidth("100%");
		form.setWidget(0, 0, nameLabel);
		form.setWidget(0, 1, nameInput);
		form.setWidget(1, 0, beschreibungLabel);
		form.setWidget(1, 1, beschreibungInput);
		final VerticalPanel root = new VerticalPanel();
		this.add(root);
		/*
		 * Falls ein selektierter Marktplatz übergeben wurde und jetzt
		 * dargestellt werden soll
		 */
		if (shouldUpdate) {
			if (changeHeadline != null) {
				root.add(changeHeadline);
			}
			nameInput.setText(toChangeMarketplace.getTitle());
			beschreibungInput.setText(toChangeMarketplace.getDescription());
			final Button saveButton = new Button("Änderungen speichern");
			saveButton.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					if (nameInput.getText().length() == 0) {
						Window.alert("Bitte vergeben Sie einen Namen");
					} else if (beschreibungInput.getText().length() == 0) {
						Window.alert("Bitte beschreiben Sie Ihren Marktplatz genauer");
					} else {
						toChangeMarketplace.setTitle(nameInput.getText());
						toChangeMarketplace.setDescription(beschreibungInput.getText());
						worketplaceAdministration.saveMarketplace(toChangeMarketplace, new AsyncCallback<Void>() {
							public void onFailure(Throwable caught) {
								Window.alert("Es trat ein Fehler beim Speichern auf, bitte versuchen Sie es erneut");
							}

							public void onSuccess(Void result) {
								Window.alert("Der Marktplatz wurde erfolgreich geändert");
							}
						});
					}
				}
			});
			final VerticalPanel panel = new VerticalPanel();
			panel.add(saveButton);
			final Button deleteButton = new Button("Diesen Marktplatz entfernen");
			deleteButton.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					final boolean confirmDelete = Window.confirm("Möchten Sie den Marktplatz wirklich löschen?");
					if (confirmDelete) {
						worketplaceAdministration.deleteMarketplace(toChangeMarketplace, new AsyncCallback<Void>() {
							public void onFailure(Throwable caught) {
								Window.alert("Es trat ein Fehler beim Löschen auf, bitte versuchen Sie es erneut");
							}

							public void onSuccess(Void result) {
								Window.alert("Der Marktplatz wurde erfolgreich gelöscht");
							}
						});
					}
				}
			});
			panel.add(deleteButton);
			form.setWidget(2, 1, panel);
		} else {
			if (addHeadline != null) {
				root.add(addHeadline);
			}
			final Button saveButton = new Button("Neuen Marktplatz anlegen");
			saveButton.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					if (nameInput.getText().length() == 0) {
						Window.alert("Bitte vergeben Sie einen Namen");
					} else if (beschreibungInput.getText().length() == 0) {
						Window.alert("Bitte beschreiben Sie Ihren Marktplatz genauer");
					} else {
						worketplaceAdministration.createMarketplace(nameInput.getText(), beschreibungInput.getText(),
								new AsyncCallback<Marketplace>() {
									public void onFailure(Throwable caught) {
										Window.alert(
												"Es trat ein Fehler beim Speichern auf, bitte versuchen Sie es erneut");
									}

									public void onSuccess(Marketplace result) {
										Window.alert("Der Marktplatz \"" + result.getTitle() + "\" wurde erstellt");
									}
								});
					}
				}
			});
			form.setWidget(2, 1, saveButton);
		}
		root.add(form);
		nameInput.setFocus(true);
	}
}