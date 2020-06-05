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