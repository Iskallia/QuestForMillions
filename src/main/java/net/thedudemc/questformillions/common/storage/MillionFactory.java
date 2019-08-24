package net.thedudemc.questformillions.common.storage;

import java.util.concurrent.Callable;

public class MillionFactory implements Callable<IMillion> {

	@Override
	public IMillion call() throws Exception {
		return new Million();
	}

}
