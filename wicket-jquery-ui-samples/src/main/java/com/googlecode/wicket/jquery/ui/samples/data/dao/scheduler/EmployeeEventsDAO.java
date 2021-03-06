package com.googlecode.wicket.jquery.ui.samples.data.dao.scheduler;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.googlecode.wicket.kendo.ui.scheduler.SchedulerEvent;

public class EmployeeEventsDAO extends AbstractSchedulerEventsDAO
{
	private static final String ROOM_ID = "roomId";
	private static final String EMPLOYEE_ID = "employeeId";

	private static EmployeeEventsDAO instance = null;

	public static synchronized EmployeeEventsDAO get()
	{
		if (instance == null)
		{
			instance = new EmployeeEventsDAO();
		}

		return instance;
	}

	protected EmployeeEventsDAO()
	{
		super();

		SchedulerEvent event1 = new SchedulerEvent(this.newId(), "Meeting #1", new Date());
		event1.setResource(ROOM_ID, 1);
		event1.setResource(EMPLOYEE_ID, Arrays.asList(1, 2));
		super.list.add(event1);

		SchedulerEvent event2 = new SchedulerEvent(this.newId(), "Meeting #2", new Date());
		event2.setResource(ROOM_ID, 2);
		event2.setResource(EMPLOYEE_ID, Arrays.asList(1, 2));
		super.list.add(event2);
	}

	@Override
	@SuppressWarnings("unchecked")
	public void update(SchedulerEvent event)
	{
		SchedulerEvent e = this.getEvent(event.getId());

		if (e != null)
		{
			e.setTitle(event.getTitle());
			e.setStart(event.getStart());
			e.setEnd(event.getEnd());
			e.setAllDay(event.isAllDay());
			e.setDescription(event.getDescription());

			e.setResource(ROOM_ID, (Integer) event.getValue(ROOM_ID));
			e.setResource(EMPLOYEE_ID, (List<Integer>) event.getValue(EMPLOYEE_ID));
		}
	}
}
