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
package org.linty.plugins.fpgametrics.measures;

import java.util.ArrayList;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.linty.plugins.fpgametrics.gsonData.JsonMetric;
import org.linty.plugins.fpgametrics.gsonData.JsonMetrics;
import org.sonar.api.config.Configuration;
//import org.sonar.api.measures.CoreMetrics;
import org.sonar.api.measures.Metric;
import org.sonar.api.measures.Metrics;
import org.sonar.api.measures.Metric.ValueType;

import com.google.gson.Gson;


import java.io.FileNotFoundException;
import java.io.FileReader;


public class MetricsImporter implements Metrics {
	
	private Configuration configuration;
	
	public MetricsImporter(Configuration configuration) {
		this.configuration=configuration;
	}
  
public static JsonMetrics jsonMetrics;
public static List<Metric> getMetricsResult;

  @Override
  public List<Metric> getMetrics() {
	  List<Metric> metrics = new ArrayList<>();
	  try {
		  //System.out.println("Working Directory = " + System.getProperty("user.dir"));
	      Gson gson = new Gson();
	      jsonMetrics = gson.fromJson(new FileReader(configuration.get("sonar.metrics.path").orElse("C:\\Program Files\\sonarqube-7.9.3\\metrics.json")), JsonMetrics.class);
	      Iterator it = jsonMetrics.metrics().entrySet().iterator();
		  while(it.hasNext()) {
			  Map.Entry me = (Map.Entry)it.next();			
			  metrics.add(new Metric.Builder((String) me.getKey(), ((JsonMetric)me.getValue()).getName(), ValueType.valueOf(((JsonMetric)me.getValue()).getType()))
					    .setDescription(((JsonMetric)me.getValue()).getDescription())
		        	    .setDirection(((JsonMetric)me.getValue()).getDirection())
		        	    .setQualitative(((JsonMetric)me.getValue()).isQualitative())
		        	    .setDomain(((JsonMetric)me.getValue()).getDomain())
		        	    .setWorstValue(((JsonMetric)me.getValue()).getWorstValue())
		        	    .setBestValue(((JsonMetric)me.getValue()).getBestValue())
		        	    .setOptimizedBestValue(((JsonMetric)me.getValue()).isOptimizedBestValue())
		        	    .setDecimalScale(((JsonMetric)me.getValue()).getDecimalScale())
		        	    .setDeleteHistoricalData(((JsonMetric)me.getValue()).isDeleteHistoricalData())
		        	    .setHidden(((JsonMetric)me.getValue()).isHidden())
		        	    .setUserManaged(((JsonMetric)me.getValue()).isUserManaged())
		        	    .create());
		  }
	      
	    } catch (FileNotFoundException e) {
	      System.out.println("Cannot find custom metrics JSON file");
	      //e.printStackTrace();
	    }
	getMetricsResult=metrics;
    return metrics;
  }
}
