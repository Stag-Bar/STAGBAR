package edu.nku.CSC440.stagbar.ui.common;

import edu.nku.CSC440.stagbar.dataaccess.data.Alcohol;

import javax.swing.*;
import java.awt.*;

public class AlcoholCheckBox extends JCheckBox {

	private final Alcohol alcohol;

	public AlcoholCheckBox(Alcohol alcohol) {
		super(alcohol.getName());
		this.alcohol = alcohol;
		setPreferredSize(new Dimension(150, -1));
	}

	public Alcohol getAlcohol() {
		return alcohol;
	}
}
