package com.sardyko.mockito.entity;

import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.sardyko.mockito.service.Service;

/**
 * Should be used when SUT class contains inner uninitialized service field,
 * used inside it's method(-s).
 */

public class EntityTest {
    private Entity entity;

    @Test
    public void oneValue() {
        // create mocking entity to replace real service
        Service service = Mockito.mock(Service.class);
        // RETURN ONE WALUE:
        Mockito.when(service.getNuberFromService()).thenReturn(1);

        // pass mocking service to class under test
        entity = new Entity(service);
        // assert correct class logic behavior
        Assert.assertEquals(entity.getNumber(), 1);
    }

    @Test
    public void multipleValues() {
        // create mocking entity to replace real service
        Service service = Mockito.mock(Service.class);
        // RETURN MULTIPLE WALUES:
        Mockito.when(service.getNuberFromService()).thenAnswer(new Answer<Integer>() {
            private int count = 0;

            public Integer answer(InvocationOnMock invocation) throws Throwable {
                return ++count;
            }
        });

        // pass mocking service to class under test
        entity = new Entity(service);
        // assert correct class logic behavior
        Assert.assertEquals(entity.getNumber(), 1);
        Assert.assertEquals(entity.getNumber(), 2);
        Assert.assertEquals(entity.getNumber(), 3);
    }

    @Test(expectedExceptions = { RuntimeException.class })
    public void throwingException() {
        // create mocking entity to replace real service
        Service service = Mockito.mock(Service.class);
        // EXCEPTION:
        Mockito.when(service.getNuberFromService()).thenThrow(new RuntimeException());

        // pass mocking service to class under test
        entity = new Entity(service);
        // assert correct class logic behavior
        entity.getNumber();
    }

    @Test
    public void vefifyMockingMethodHadBeenCalled() {
        // create mocking entity to replace real service
        Service service = Mockito.mock(Service.class);
        // Add mocking logic
        Mockito.when(service.getNuberFromService()).thenReturn(3);

        // pass mocking service to class under test
        entity = new Entity(service);
        // call method on mocking service inside SUT
        entity.getNumber();

        // verify that mocking method had been called(it easier to do this with Mockito
        // than with stub objects)
        Mockito.verify(service).getNuberFromService();
    }
}
