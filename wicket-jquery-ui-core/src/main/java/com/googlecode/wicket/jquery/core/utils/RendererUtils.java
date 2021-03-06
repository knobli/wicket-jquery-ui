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
package com.googlecode.wicket.jquery.core.utils;

import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.core.renderer.IChoiceRenderer;
import com.googlecode.wicket.jquery.core.renderer.ITextRenderer;

/**
 * Utility class for renderers
 *
 * @author Sebastien Briquet - sebfz1
 *
 */
public class RendererUtils
{
	// ITextRenderer //

	/**
	 * Transforms the object in a JSON object
	 *
	 * @param object the object
	 * @param renderer the {@link ITextRenderer}
	 * @return the JSON object
	 */
	public static <T> String toJson(final T object, final ITextRenderer<? super T> renderer)
	{
		return String.format("{ %s }", RendererUtils.getJsonBody(object, renderer));
	}

	/**
	 * Gets the JSON body of an object
	 *
	 * @param object the object
	 * @param renderer the {@link ITextRenderer}
	 * @return the JSON body
	 */
	public static <T> String getJsonBody(final T object, final ITextRenderer<? super T> renderer)
	{
		return String.format("%1$s%2$s%1$s: %1$s%3$s%1$s", Options.QUOTE, renderer.getTextField(), renderer.getText(object));
	}

	/**
	 * Gets the JSON body of an object property
	 *
	 * @param object the object
	 * @param renderer the {@link ITextRenderer}
	 * @param property the object property
	 * @return the JSON body
	 */
	public static <T> String getJsonBody(final T object, final ITextRenderer<? super T> renderer, final String property)
	{
		return String.format("%1$s%2$s%1$s: %1$s%3$s%1$s", Options.QUOTE, property, renderer.getText(object, property));
	}

	// IChoiceRenderer //

	/**
	 * Transforms the object in a JSON object
	 *
	 * @param object the object
	 * @param renderer the {@link ITextRenderer}
	 * @return the JSON object
	 */
	public static <T> String toJson(final T object, final IChoiceRenderer<? super T> renderer)
	{
		return String.format("{ %s }", RendererUtils.getJsonBody(object, renderer));
	}

	/**
	 * Gets the JSON body of an object
	 *
	 * @param object the object
	 * @param renderer the {@link ITextRenderer}
	 * @return the JSON body
	 */
	public static <T> String getJsonBody(final T object, final IChoiceRenderer<? super T> renderer)
	{
		return String.format("%1$s%2$s%1$s: %1$s%3$s%1$s, %1$s%4$s%1$s: %1$s%5$s%1$s", Options.QUOTE, renderer.getTextField(), renderer.getText(object), renderer.getValueField(), renderer.getValue(object));
	}

	/**
	 * Utility class
	 */
	private RendererUtils()
	{
	}
}
