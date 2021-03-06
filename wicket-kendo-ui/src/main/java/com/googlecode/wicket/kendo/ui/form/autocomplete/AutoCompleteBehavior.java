/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.googlecode.wicket.kendo.ui.form.autocomplete;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.CallbackParameter;

import com.googlecode.wicket.jquery.core.JQueryEvent;
import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.core.ajax.IJQueryAjaxAware;
import com.googlecode.wicket.jquery.core.ajax.JQueryAjaxBehavior;
import com.googlecode.wicket.jquery.core.utils.RequestCycleUtils;
import com.googlecode.wicket.kendo.ui.KendoUIBehavior;
import com.googlecode.wicket.kendo.ui.utils.DebugUtils;

/**
 * Provides a Kendo UI auto-complete behavior
 *
 * @author Sebastien Briquet - sebfz1
 *
 */
public abstract class AutoCompleteBehavior extends KendoUIBehavior implements IJQueryAjaxAware, IAutoCompleteListener
{
	private static final long serialVersionUID = 1L;
	private static final String METHOD = "kendoAutoComplete";

	private JQueryAjaxBehavior onSelectBehavior = null;

	/**
	 * Constructor
	 *
	 * @param selector the html selector (ie: "#myId")
	 */
	public AutoCompleteBehavior(String selector)
	{
		this(selector, new Options());
	}

	/**
	 * Constructor
	 *
	 * @param selector the html selector (ie: "#myId")
	 * @param options the {@link Options}
	 */
	public AutoCompleteBehavior(String selector, Options options)
	{
		super(selector, METHOD, options);
	}

	// Methods //

	@Override
	public void bind(Component component)
	{
		super.bind(component);

		component.add(this.onSelectBehavior = this.newOnSelectBehavior());
	}

	protected abstract CharSequence getChoiceCallbackUrl();

	// Events //

	@Override
	public void onConfigure(Component component)
	{
		super.onConfigure(component);

		this.setOption("autoBind", true); // immutable
		this.setOption("serverFiltering", true);
		this.setOption("dataSource", this.newDataSource());
		this.setOption("select", this.onSelectBehavior.getCallbackFunction());
	}

	// IJQueryAjaxAware //

	@Override
	public void onAjax(AjaxRequestTarget target, JQueryEvent event)
	{
		if (event instanceof SelectEvent)
		{
			this.onSelect(target, ((SelectEvent) event).getIndex());
		}
	}

	// Factories //

	/**
	 * Gets a new DataSource
	 * 
	 * @return the new DataSource 
	 */
	protected String newDataSource()
	{
		return String.format("{ serverFiltering: true, transport: { read: { url: '%s', dataType: 'json' } }, error: %s }", this.getChoiceCallbackUrl(), DebugUtils.errorCallback);
	}

	/**
	 * Gets a new {@link JQueryAjaxBehavior} that will be called on 'select' javascript method
	 *
	 * @return the {@link JQueryAjaxBehavior}
	 */
	protected JQueryAjaxBehavior newOnSelectBehavior()
	{
		return new JQueryAjaxBehavior(this) {

			private static final long serialVersionUID = 1L;

			@Override
			protected CallbackParameter[] getCallbackParameters()
			{
				return new CallbackParameter[] { CallbackParameter.context("e"), CallbackParameter.resolved("index", "e.item.index()"), CallbackParameter.resolved("value", "e.item.text") };
			}

			@Override
			protected JQueryEvent newEvent()
			{
				return new SelectEvent();
			}
		};
	}

	// Event classes //

	/**
	 * Provides an event object that will be broadcasted by the {@link JQueryAjaxBehavior} select callback
	 *
	 */
	protected static class SelectEvent extends JQueryEvent
	{
		private final int index;
		private final String value;

		public SelectEvent()
		{
			this.index = RequestCycleUtils.getQueryParameterValue("index").toInt(-1);
			this.value = RequestCycleUtils.getQueryParameterValue("value").toString();
		}

		public int getIndex()
		{
			return this.index;
		}

		public String getValue()
		{
			return this.value;
		}
	}
}
