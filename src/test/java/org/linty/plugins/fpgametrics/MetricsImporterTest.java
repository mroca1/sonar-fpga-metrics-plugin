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
import org.linty.plugins.fpgametrics.measures.MetricsImporter;
import org.sonar.api.config.internal.MapSettings;
import org.sonar.api.measures.Metric.ValueType;

import static org.junit.Assert.assertEquals;

public class MetricsImporterTest {


    
    @Test
    public void execute_WithMatchingPattern_ExpectValueRegistered() throws Exception {

        final MetricsImporter metricsImporter = new MetricsImporter(new  MapSettings().setProperty("sonar.metrics.path", "src\\test\\files\\format-metrics.json").asConfig());
        metricsImporter.getMetrics();
        assertEquals(3,metricsImporter.getMetricsResult().size());
        assertEquals("float_metric",metricsImporter.getMetricsResult().get(0).getName());
        assertEquals(ValueType.INT,metricsImporter.getMetricsResult().get(1).getType());
        assertEquals(true,metricsImporter.getMetricsResult().get(2).getQualitative());
    }


}