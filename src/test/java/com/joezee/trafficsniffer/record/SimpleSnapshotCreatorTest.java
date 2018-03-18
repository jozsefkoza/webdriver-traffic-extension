package com.joezee.trafficsniffer.record;

import com.joezee.trafficsniffer.TestResourceUtils;
import net.lightbody.bmp.BrowserMobProxy;
import org.assertj.core.api.Condition;
import org.mockito.Mock;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Unit test for {@link SimpleTrafficSnapshotter}.
 *
 * @author JoeZee
 */
public class SimpleSnapshotCreatorTest {

    @Mock
    private BrowserMobProxy proxy;

    private SimpleTrafficSnapshotter recorder;

    @BeforeMethod
    public void setUp() {
        initMocks(this);
        recorder = new SimpleTrafficSnapshotter(proxy);
        when(proxy.getHar()).thenReturn(TestResourceUtils.getTestHar());
    }

    @Test
    public void shouldTransformCapturedTraffic() {
        Snapshot trafficElements = recorder.takeSnapshot();

        assertThat(trafficElements).is(new Condition<>(snapshot -> snapshot.size() == 1, null));
        trafficElements.trafficElements().forEach(trafficElement -> assertThat(trafficElement).isInstanceOf(SimpleTrafficElement.class));
    }
}
