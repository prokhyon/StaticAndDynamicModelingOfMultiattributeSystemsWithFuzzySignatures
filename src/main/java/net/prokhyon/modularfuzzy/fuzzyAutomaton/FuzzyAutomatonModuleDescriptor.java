package net.prokhyon.modularfuzzy.fuzzyAutomaton;

import javafx.scene.layout.FlowPane;
import net.prokhyon.modularfuzzy.api.ModuleDescriptor;
import net.prokhyon.modularfuzzy.common.CommonServices;
import net.prokhyon.modularfuzzy.common.modules.DefaultModelLoaderInfo;
import net.prokhyon.modularfuzzy.common.modules.FxModulesViewInfo;
import net.prokhyon.modularfuzzy.common.modules.PersistableModelInfo;
import net.prokhyon.modularfuzzy.common.modules.WorkspaceInfo;
import net.prokhyon.modularfuzzy.fuzzyAutomaton.util.ModelDomainIOManager;
import net.prokhyon.modularfuzzy.shell.services.ServiceFactory;

public class FuzzyAutomatonModuleDescriptor implements ModuleDescriptor {

	private CommonServices services;

	private final String VIEW_NAME = "Automatons";
	private FxModulesViewInfo fxModulesViewInfo;
	private PersistableModelInfo persistableModelInfo;
	private WorkspaceInfo workspaceInfo;

	@Override
	public void initializeModule() {
		services = new ServiceFactory().getCommonServices();

		this.fxModulesViewInfo = new FxModulesViewInfo("Fuzzy Automaton Editor",
				"view/FuzzyAutomatonEditorLayout.fxml", FuzzyAutomatonModuleDescriptor.class, FlowPane.class);
		services.registerView(fxModulesViewInfo);

		this.persistableModelInfo = new PersistableModelInfo(new ModelDomainIOManager(),
				net.prokhyon.modularfuzzy.fuzzyAutomaton.model.ModelConverter.class,
				null,
				null,
				net.prokhyon.modularfuzzy.fuzzyAutomaton.model.fx.FuzzyAutomaton.class,
				net.prokhyon.modularfuzzy.fuzzyAutomaton.model.fx.FuzzyAutomaton.class,
				net.prokhyon.modularfuzzy.fuzzyAutomaton.model.descriptor.FuzzyAutomaton.class,
				net.prokhyon.modularfuzzy.fuzzyAutomaton.model.descriptor.FuzzyState.class,
				net.prokhyon.modularfuzzy.fuzzyAutomaton.model.descriptor.FuzzyTransition.class);

		this.workspaceInfo = new WorkspaceInfo(VIEW_NAME, fxModulesViewInfo, persistableModelInfo);

		services.<net.prokhyon.modularfuzzy.fuzzyAutomaton.model.fx.FuzzyAutomaton> registerModelTypeInStore(workspaceInfo);

		services.registerDefaultModelLoader(new DefaultModelLoaderInfo("Load Automatons", persistableModelInfo));
	}

	public String getViewName() {
		return VIEW_NAME;
	}

	public FxModulesViewInfo getFxModulesViewInfo() {
		return fxModulesViewInfo;
	}

	public PersistableModelInfo getPersistableModelInfo() {
		return persistableModelInfo;
	}

	public WorkspaceInfo getWorkspaceInfo() {
		return workspaceInfo;
	}

}
