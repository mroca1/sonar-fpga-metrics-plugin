 /*
 * sonar-fpga-metrics plugin for Sonarqube
 * Copyright (C) 2020 Linty Services
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.linty.plugins.fpgametrics;


import org.junit.Test;
import org.linty.plugins.fpgametrics.measures.MeasuresImporter;
import org.sonar.api.batch.sensor.internal.SensorContextTester;
import org.sonar.api.batch.sensor.measure.Measure;
import org.sonar.api.measures.Metric;
import org.sonar.api.measures.Metric.ValueType;

import java.io.File;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class MeasuresImporterTest {

    
    @Test
    public void execute_WithMatchingPattern_ExpectValueRegistered() throws Exception {
    	
        final SensorContextTester contextTester = SensorContextTester.create(new File("src\\test\\files\\ctx")); //Blank file only used for context simulation

        final MeasuresImporter mesuresImporter = new MeasuresImporter(Arrays.asList(FLOAT_METRIC, INT_METRIC, STRING_METRIC),"src\\test\\files\\");
        mesuresImporter.execute(contextTester);

        final Measure<Double> floatMeasure = contextTester.measure(contextTester.module().key(), FLOAT_METRIC.key());
        final Measure<Integer> intMeasure = contextTester.measure(contextTester.module().key(), INT_METRIC.key());
        final Measure<String> stringMeasure = contextTester.measure(contextTester.module().key(), STRING_METRIC.key());
        assertEquals((Double) 80.0, floatMeasure.value());
        assertEquals((Integer)10, intMeasure.value());
        assertEquals("hello", stringMeasure.value());
    }


    static final Metric<Double> FLOAT_METRIC = new Metric.Builder("float_metric", "Float metric", ValueType.FLOAT).create();
    static final Metric<Integer> INT_METRIC = new Metric.Builder("int_metric", "Int metric", ValueType.INT).create();
    static final Metric<String> STRING_METRIC = new Metric.Builder("string_metric", "String metric", ValueType.STRING).create();

}