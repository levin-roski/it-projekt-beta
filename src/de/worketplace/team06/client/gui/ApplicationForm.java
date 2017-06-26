package de.worketplace.team06.client.gui;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.worketplace.team06.client.Callback;
import de.worketplace.team06.client.ClientsideSettings;
import de.worketplace.team06.client.Form;
import de.worketplace.team06.shared.WorketplaceAdministrationAsync;
import de.worketplace.team06.shared.bo.Application;

/**
 * Formular für die Darstellung, Bearbeitung und Löschung einer selektierten
 * Bewerbung. Falls keine selektierte Bewerbung beim Initialisieren übergeben
 * wird, ist das Formular leer, bereit für die Erstellung einer neuen Bewerbung.
 * 
 * @author Patrick
 */
public class ApplicationForm extends Form {
	private Label textLabel = new Label("Bewerbungstext");
	private TextBox textInput = new TextBox();
	private Label statusLabel = new Label("Bewerbungsstatus");
	private Label bewertungLabel = new Label("Bewerbungsbewertung");
	private boolean shouldUpdate = false;
	private Application toChangeApplication;
	private HorizontalPanel changeHeadline;
	private HorizontalPanel addHeadline;

	/**
	 * Im Konstruktor kann eine selektierte Bewerbung übergeben werden, die dann
	 * bearbeitet und gelöscht werden kann. null wird übergeben, falls eine neue
	 * Bewerbung erstellt werden soll.
	 * 
	 * @param pToChangeApplication
	 *            Application, die im Formular angezeigt werden soll
	 * @param pHeadline
	 *            Falls true wird dem Formular eine Überschrift vorangehängt
	 */
	public ApplicationForm(Application pToChangeApplication, final boolean pHeadline) {
		if (pToChangeApplication != null) {
			shouldUpdate = true;
			this.toChangeApplication = pToChangeApplication;
		}
		if (pHeadline) {
			changeHeadline = createHeadline("Bewerbung bearbeiten", true);
			addHeadline = createHeadline("Auf Projekt bewerben", true);
		}
	}

	/**
	 * Im Konstruktor kann eine selektierte Bewerbung übergeben werden, die dann
	 * bearbeitet und gelöscht werden kann. null übergeben, falls eine neue
	 * Bewerbung erstellt werden soll.
	 * 
	 * @param pToChangeApplication
	 *            Application, die im Formular angezeigt werden soll
	 * @param pHeadline
	 *            Falls true wird dem Formular eine Überschrift vorangehängt
	 * @param pClosingHeadline
	 *            Falls true wird dem Formular eine Überschrift mit Button, der
	 *            das aktuelle Item schließt, vorangehängt
	 */
	public ApplicationForm(Application pToChangeApplication, final boolean pHeadline, final boolean pClosingHeadline,
			final Callback editCallback, final Callback deleteCallback) {
		this(pToChangeApplication, pHeadline);
		if (pClosingHeadline) {
			changeHeadline = createHeadlineWithCloseButton("Bewerbung bearbeiten", true);
			addHeadline = createHeadlineWithCloseButton("Auf Projekt bewerben", true);
		}

		/*
		 * Grid mit 3 Zeilen und 2 Spalten für das Formular bereitstellen.
		 * Danach nötige Panels einfügen und diesem Widget hinzufügen.
		 */
		Grid form = new Grid(3, 2);
		form.setWidth("100%");
		form.setWidget(0, 0, textLabel);
		form.setWidget(0, 1, textInput);
		form.setWidget(1, 0, statusLabel);
		form.setWidget(1, 1, bewertungLabel);
		final VerticalPanel root = new VerticalPanel();
		this.add(root);
		/*
		 * Falls eine selektierte Bewerbung übergeben wurde und jetzt
		 * dargestellt werden soll
		 */
		if (shouldUpdate) {
			if (changeHeadline != null) {
				root.add(changeHeadline);
			}
			textInput.setText(toChangeApplication.getText());
			final Button saveButton = new Button("Änderungen speichern");
			saveButton.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					if (textInput.getText().length() == 0) {
						Window.alert("Bitte geben Sie einen Bewerbungstext ein");
					} else {
						toChangeApplication.setText(textInput.getText());
						worketplaceAdministration.saveApplication(toChangeApplication, new AsyncCallback<Void>() {
							public void onFailure(Throwable caught) {
								Window.alert("Es trat ein Fehler beim Speichern auf, bitte versuchen Sie es erneut");
							}

							public void onSuccess(Void result) {
								Window.alert("Die Bewerbung wurde erfolgreich geändert");
								if (editCallback != null) {
									editCallback.run();
								} else {
									renderFormSuccess();
								}
							}
						});
					}
				}
			});
			final VerticalPanel panel = new VerticalPanel();
			panel.add(saveButton);
			final Button deleteButton = new Button("Bewerbung löschen");
			deleteButton.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					final boolean confirmDelete = Window.confirm("Möchten Sie die Bewerbung wirklich löschen?");
					if (confirmDelete) {
						worketplaceAdministration.deleteApplication(toChangeApplication, new AsyncCallback<Void>() {
							public void onFailure(Throwable caught) {
								Window.alert("Es trat ein Fehler beim Löschen auf, bitte versuchen Sie es erneut");
							}

							public void onSuccess(Void result) {
								Window.alert("Die Bewerbung wurde erfolgreich gelöscht");
								if (deleteCallback != null) {
									deleteCallback.run();
								} else {
									renderFormSuccess();
								}
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
			final Button saveButton = new Button("Auf Projekt bewerben");
			saveButton.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					if (textInput.getText().length() == 0) {
						Window.alert("Bitte geben Sie einen Bewerbungstext ein");
					} else {
						worketplaceAdministration.createApplication(textInput.getText(),
								new AsyncCallback<Application>() {
									public void onFailure(Throwable caught) {
										Window.alert(
												"Es trat ein Fehler beim Speichern auf, bitte versuchen Sie es erneut");
									}

									public void onSuccess(Application result) {
										Window.alert("Die Bewerbung auf die Ausschreibung \"" + result.getCallTitle()
												+ "\" wurde erstellt");
										renderFormSuccess();
									}
								});
					}
				}
			});
			form.setWidget(2, 1, saveButton);
		}
		root.add(form);
	}
}