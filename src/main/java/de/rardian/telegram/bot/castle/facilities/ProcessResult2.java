package de.rardian.telegram.bot.castle.facilities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import de.rardian.telegram.bot.command.action.ResultAction;

public class ProcessResult2 {
	private Collection<ResultAction> actions = new ArrayList<>();
	private Map<String, Integer> integers = new HashMap<>();

	public void addResultAction(ResultAction action) {
		actions.add(action);
	}

	public Collection<ResultAction> getResultActions() {
		return new ArrayList<>(actions);
	}

	public void addResultInteger(String key, Integer value) {
		integers.put(key, value);
	}

	public Integer getResultInteger(String key) {
		return integers.get(key);
	}
}
