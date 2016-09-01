package com.sms.command;

public class ConsoleLoadingBar {
	private String text;
	private Thread runner;
	private int currentSequence;

	public ConsoleLoadingBar(String text) {
		this.setText(text);
	}

	public void startAnimation() {
		runner = new Thread(new Runnable() {
			@Override
			public void run() {
				while (!Thread.currentThread().isInterrupted()) {
					try {
						Thread.sleep(250);
					} catch (InterruptedException e) {
						// good!
						return;
					}
					System.out.print("\r" + text + " " + nextSequence());
				}
			}
		}, "Loading-Bar");
		runner.start();
	}

	private char[] symbols = { '|', '/', '-', '\\' };

	private char nextSequence() {
		return symbols[currentSequence++ % 4];
	}

	public void stopAnimation() {
		runner.interrupt();
		System.out.print("\r" + text);
	}

	public String getText() {
		return text;
	}

	public void join() throws InterruptedException {
		runner.join();
	}

	public void setText(String text) {
		this.text = text;
	}
}