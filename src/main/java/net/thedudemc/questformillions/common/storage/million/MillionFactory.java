package net.thedudemc.questformillions.common.storage.million;

import java.util.concurrent.Callable;

public class MillionFactory implements Callable<IMillion> {

	@Override
	public IMillion call() throws Exception {
		return new Million();
	}

}
