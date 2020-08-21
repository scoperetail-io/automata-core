package com.scoperetail.automata.core.fixtures.random;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * @author scoperetail
 *
 */
@Service("randomWorkService")
@Transactional
//Sample Code
@SuppressWarnings("squid:S106")
public class RandomWorkServiceImpl implements RandomWorkService{

	@Override
	public void doSomeWork() {
		System.out.println("RandomWorkServiceImpl.doSomeWork()");
	}

	@Override
	public void doSomeMoreWork() {
		System.out.println("RandomWorkServiceImpl.doSomeMoreWork()");
	}

	@Override
	public boolean checkSomething(String something) {
		return something.equalsIgnoreCase("SAM'S CHOICE");
	}

}
