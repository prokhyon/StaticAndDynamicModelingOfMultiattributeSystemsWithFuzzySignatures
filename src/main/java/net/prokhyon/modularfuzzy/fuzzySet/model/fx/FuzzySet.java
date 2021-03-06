package net.prokhyon.modularfuzzy.fuzzySet.model.fx;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.Observable;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.util.Callback;
import net.prokhyon.modularfuzzy.common.conversion.ConvertibleFxModel2Descriptor;
import net.prokhyon.modularfuzzy.common.errors.ModuleImplementationException;
import net.prokhyon.modularfuzzy.common.modelFx.WorkspaceFxElementBase;
import net.prokhyon.modularfuzzy.fuzzySet.model.descriptor.*;

public class FuzzySet extends WorkspaceFxElementBase
		implements ConvertibleFxModel2Descriptor.Internal<FuzzySetBase, FuzzySet> {

	private final StringProperty fuzzySetName;
	private final StringProperty fuzzySetDescription;
	private final ObjectProperty<FuzzySetTypeEnum> fuzzySetType;
	private final ListProperty<FuzzySetPoint> fuzzySetPoints;

	public static Callback<FuzzySet, Observable[]> extractor() {
		return new Callback<FuzzySet, Observable[]>() {
			@Override
			public Observable[] call(FuzzySet param) {
				return new Observable[] { param.fuzzySetNameProperty(), param.fuzzySetDescriptionProperty(),
						param.fuzzySetTypeProperty(), param.fuzzySetPointsProperty() };
			}
		};
	}

	public FuzzySet(String fuzzySetName, String fuzzySetDescription, FuzzySetTypeEnum fuzzySetType,
					List<FuzzySetPoint> fuzzySetPoints) {
		super();
		this.fuzzySetName = new SimpleStringProperty(fuzzySetName);
		this.fuzzySetDescription = new SimpleStringProperty(fuzzySetDescription);
		this.fuzzySetType = new SimpleObjectProperty<>(fuzzySetType);
		this.fuzzySetPoints = new SimpleListProperty<>(FXCollections.observableArrayList(fuzzySetPoints != null ? fuzzySetPoints : new ArrayList<>()));
	}

	public FuzzySet(FuzzySet otherFuzzySet) {
		this(otherFuzzySet.fuzzySetName.get(), otherFuzzySet.fuzzySetDescription.get(),
				otherFuzzySet.fuzzySetType.get(), otherFuzzySet.fuzzySetPoints.get());
	}

	public FuzzySet deepCopy() {
		return new FuzzySet(this.fuzzySetName.get(), this.fuzzySetDescription.get(),
				this.fuzzySetType.get(), deepCopyFuzzySetPoints(this.fuzzySetPoints.get()));
	}

	private List<FuzzySetPoint> deepCopyFuzzySetPoints(List<FuzzySetPoint> fuzzySetPoints){

		if (fuzzySetPoints != null) {
			List<FuzzySetPoint> copiedFuzzySetPoints = new ArrayList<>();
			for (FuzzySetPoint fs : fuzzySetPoints) {
				copiedFuzzySetPoints.add(fs.deepCopy());
			}
			return copiedFuzzySetPoints;
		}
		return fuzzySetPoints;
	}

	public String getFuzzySetName() {
		return fuzzySetName.get();
	}

	public void setFuzzySetName(String fuzzySetName) {
		this.fuzzySetName.set(fuzzySetName);
	}

	public StringProperty fuzzySetNameProperty() {
		return fuzzySetName;
	}

	public String getFuzzySetDescription() {
		return fuzzySetDescription.get();
	}

	public void setFuzzySetDescription(String fuzzySetDescription) {
		this.fuzzySetDescription.set(fuzzySetDescription);
	}

	public StringProperty fuzzySetDescriptionProperty() {
		return fuzzySetDescription;
	}

	public FuzzySetTypeEnum getFuzzySetType() {
		return fuzzySetType.get();
	}

	public void setFuzzySetType(FuzzySetTypeEnum fuzzySetType) {
		this.fuzzySetType.set(fuzzySetType);
	}

	public ObjectProperty<FuzzySetTypeEnum> fuzzySetTypeProperty() {
		return fuzzySetType;
	}

	public List<FuzzySetPoint> getFuzzySetPoints() {
		return fuzzySetPoints.get();
	}

	public void setFuzzySetPoints(List<FuzzySetPoint> fuzzySetPoints) {
		this.fuzzySetPoints.set(FXCollections.observableArrayList(fuzzySetPoints));
	}

	public ListProperty<FuzzySetPoint> fuzzySetPointsProperty() {
		return fuzzySetPoints;
	}

	@Override
	public FuzzySetBase convert2DescriptorModel() {

		final List<FuzzySetPoint> fxFuzzySetPoints = getFuzzySetPoints();
		List<FuzzyPointBase> descriptorFuzzySetPoints = new ArrayList<>();
		for (FuzzySetPoint fxFuzzySetPoint : fxFuzzySetPoints){
			FuzzyPointBase fuzzyPointBase = fxFuzzySetPoint.convert2DescriptorModel();
			descriptorFuzzySetPoints.add(fuzzyPointBase);
		}
		if (this.getFuzzySetType().equals(FuzzySetTypeEnum.TRIANGULAR)){
			return new FuzzySetTriangular( getFuzzySetName(), getFuzzySetDescription(), descriptorFuzzySetPoints);
		} else if (this.getFuzzySetType().equals(FuzzySetTypeEnum.POLYGONAL)) {
			return new FuzzySetPolygonal( getFuzzySetName(), getFuzzySetDescription(), descriptorFuzzySetPoints);
		} else if (this.getFuzzySetType().equals(FuzzySetTypeEnum.TRAPEZOID)) {
			return new FuzzySetTrapezoidal( getFuzzySetName(), getFuzzySetDescription(), descriptorFuzzySetPoints);
		}
		throw new ModuleImplementationException("Unknown FuzzySetTypeEnum while converting to FuzzySetBase descriptor model.");
	}

}
