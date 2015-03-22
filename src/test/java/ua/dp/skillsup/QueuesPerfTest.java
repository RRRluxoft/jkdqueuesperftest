package ua.dp.skillsup;


import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.*;

/**
 * @author Andrey Lomakin <a href="mailto:lomakin.andrey@gmail.com">Andrey Lomakin</a>
 * @since 10/26/14
 */
public class QueuesPerfTest {
	private static final int WARMUP = 500000;

	private static final int REPETITIONS = 20000000;
	private static final int PRODUCERS = 4;
	private static final Integer TEST_VALUE = 42;
	private final ExecutorService executorService = Executors.newSingleThreadExecutor();


	@Test
	public void testArrayQueue() throws Exception {
		Queue<Integer> queue = new ArrayBlockingQueue<Integer>(1000);

		perfTest(queue);

	}


	@Test
	public void testLinkedQueue() throws Exception {
		Queue<Integer> queue = new ConcurrentLinkedQueue<Integer>();

		perfTest(queue);
	}


	private void perfTest(Queue<Integer> queue) throws Exception {
		performanceRun(0, queue);
		final long duration = performanceRun(1, queue);


		final long ops = (PRODUCERS * REPETITIONS * 1000L * 1000L * 1000L) / duration;
		System.out.format("ops/sec=%,d - %s\n", ops, queue.getClass()
						.getSimpleName());
	}


	private long performanceRun(int runNumber, final Queue<Integer> queue) throws Exception {
		if (runNumber == 0) {
			Future<Void> producer = executorService.submit(new Producer(queue, runNumber));

			int i = WARMUP;
			do {
				while (null == (queue.poll())) {
				}
			} while (0 != --i);
			producer.get();

			return 0;
		} else {
			final List<Future<Void>> producers = new ArrayList<Future<Void>>();

			for (int k = 0; k < PRODUCERS; k++) {
				producers.add(executorService.submit(new Producer(queue, runNumber)));
			}


			final long start = System.nanoTime();
			int i = REPETITIONS * PRODUCERS;

			do {
				while (null == (queue.poll())) {
				}
			} while (0 != --i);

			for (Future producer : producers)
				producer.get();

			final long duration = System.nanoTime() - start;
			return duration;
		}
	}


	public final class Producer implements Callable<Void> {
		private final Queue<Integer> queue;
		private final int runNumber;

		public Producer(Queue<Integer> queue, int runNumber) {
			this.queue = queue;
			this.runNumber = runNumber;
		}

		@Override
		public Void call() throws Exception {
			int i;
			if (runNumber == 0)
				i = WARMUP;
			else
				i = REPETITIONS;

			do {
				while (!queue.offer(TEST_VALUE)) {
				}
			} while (0 != --i);

			return null;
		}
	}
}