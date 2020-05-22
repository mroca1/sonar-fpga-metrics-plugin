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


import org.sonar.api.config.Configuration;
//import org.sonar.api.measures.CoreMetrics;
import org.sonar.api.measures.Metric;
import org.sonar.api.measures.Metrics;
import org.sonar.api.measures.Metric.ValueType;

import com.google.gson.Gson;


import java.io.FileNotFoundException;
import java.io.FileReader;

class JsonMetrics {
    private Map<String, JsonMetric> metrics;
    public Map<String, JsonMetric> metrics(){
    	return metrics;
    }
}

class JsonMetric {
	  public String getName() {
		return name;
	}
	public String getType() {
		return type;
	}
	public String getDescription() {
		return description;
	}
	public boolean isQualitative() {
		return qualitative;
	}
	
	public String getDomain() {
		return domain;
	}

	public Double getWorstValue() {
		return worstValue;
	}

	public Double getBestValue() {
		return bestValue;
	}

	private String name;
	private String type;
	private String description;
	private String domain;
	private Double worstValue;
	private Double bestValue;
	private boolean qualitative;
	  
	}

public class MetricsImporter implements Metrics {
	
	private Configuration configuration;
	
	public MetricsImporter(Configuration configuration) {
		this.configuration=configuration;
	}

  /*public static final Metric<Integer> FILENAME_SIZE = new Metric.Builder("filename_size", "Filename Size", Metric.ValueType.INT)
    .setDescription("Number of characters of file names")
    .setDirection(Metric.DIRECTION_BETTER)
    .setQualitative(false)
    .setDomain(CoreMetrics.DOMAIN_GENERAL)
    .create();

  public static final Metric<Integer> FILENAME_SIZE_RATING = new Metric.Builder("filename_size_rating", "Filename Size Rating", Metric.ValueType.RATING)
    .setDescription("Rating based on size of file names")
    .setDirection(Metric.DIRECTION_BETTER)
    .setQualitative(true)
    .setDomain(CoreMetrics.DOMAIN_GENERAL)
    .create();*/
  
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
		        	    .setDirection(Metric.DIRECTION_BETTER)
		        	    .setQualitative(((JsonMetric)me.getValue()).isQualitative())
		        	    .setDomain(((JsonMetric)me.getValue()).getDomain())
		        	    .setWorstValue(((JsonMetric)me.getValue()).getWorstValue())
		        	    .setBestValue(((JsonMetric)me.getValue()).getBestValue())	    
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
