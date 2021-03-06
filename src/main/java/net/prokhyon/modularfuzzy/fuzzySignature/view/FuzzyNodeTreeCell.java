package net.prokhyon.modularfuzzy.fuzzySignature.view;

import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTreeCell;
import net.prokhyon.modularfuzzy.common.errors.ModelError;
import net.prokhyon.modularfuzzy.fuzzySignature.model.fx.CompoundFuzzyAutomaton;
import net.prokhyon.modularfuzzy.fuzzySignature.model.fx.FuzzyNode;
import net.prokhyon.modularfuzzy.shell.services.ServiceFactory;


public class FuzzyNodeTreeCell extends TextFieldTreeCell<FuzzyNode> {

	private ContextMenu rootContextMenu;

	public FuzzyNodeTreeCell(FuzzySignatureEditorController fuzzySignatureEditorController) {

		rootContextMenu = new ContextMenu();
		MenuItem addMenuItem = new MenuItem("Add child node");
		MenuItem removeMenuItem = new MenuItem("Remove subtree");
		MenuItem loadSubtreeMenuItem = new MenuItem("Load existing subtree");
		MenuItem viewAutomatonMenuItem = new MenuItem("View automaton");

		rootContextMenu.getItems().add(addMenuItem);
		rootContextMenu.getItems().add(removeMenuItem);
		rootContextMenu.getItems().add(loadSubtreeMenuItem);
		rootContextMenu.getItems().add(viewAutomatonMenuItem);

		addMenuItem.setOnAction(t -> {

			final TreeItem<FuzzyNode> item = getTreeItem();
			final FuzzyNode thisNode = (item != null ? item.getValue() : null);

			// Handling tree in fuzzy model
			final FuzzyNode fuzzyNode = new FuzzyNode("Node" + fuzzySignatureEditorController.getNextNodeCounterValue(), thisNode, null, null, null, null);
			thisNode.getChildNodes().add(fuzzyNode);

			// Handling tree in TreeView
			TreeItem fuzzyNodeTreeItem = new TreeItem<>(fuzzyNode);
			getTreeItem().getChildren().add(fuzzyNodeTreeItem);
		});

		removeMenuItem.setOnAction(t -> {

			final TreeItem<FuzzyNode> item = getTreeItem();
			final TreeItem<FuzzyNode> parent = item.getParent();

			// Handling tree in TreeView
			if (parent != null)
				parent.getChildren().remove(item);
			t.consume();

			// Handling tree in fuzzy model
			final FuzzyNode thisNode = (item != null ? item.getValue() : null);
			final FuzzyNode parentNode = (parent != null ? parent.getValue() : null);
			if (parentNode != null)
				parentNode.getChildNodes().remove(thisNode);
		});

		viewAutomatonMenuItem.setOnAction(t -> {

			final TreeItem<FuzzyNode> item = getTreeItem();
			final FuzzyNode thisNode = (item != null ? item.getValue() : null);

			fuzzySignatureEditorController.showCompoundAutomaton(thisNode);
		});

	}

	@Override
	public void updateItem(FuzzyNode item, boolean empty) {
		super.updateItem(item, empty);

		if (!empty) {
			setContextMenu(rootContextMenu);
		}

		if (item != null) {
			String str = null;
			try {
				str = item.getFuzzyNodeName();
			} catch (Exception e) {}
			setText(str + getItemDescriptingInformation(item));
		} else
			setText(null);
	}

	private String getItemDescriptingInformation(FuzzyNode item) {

		if (item.getChildNodes().size() > 0) {
			return (item.getAggregationType() != null ? " : <" + item.getAggregationType().toString() + ">" : "");
		} else {
			return (item.getFuzzyAutomaton() != null ? " : [" + item.getFuzzyAutomaton().getFuzzyAutomatonName() + "]" : "");
		}
	}

}