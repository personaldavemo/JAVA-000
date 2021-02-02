package com.springsource.study.demo.model;

import org.springframework.beans.factory.FactoryBean;

public class CarBean implements FactoryBean<Car> {
    private String carInfos;
    @Override
    public Car getObject() throws Exception {
        Car car = new Car();
        String[] infos = carInfos.split(",");
        car.setBrand(infos[0]);
        car.setMaxSpeed(Integer.parseInt(infos[1]));
        car.setPrice(Double.parseDouble(infos[2]));
        return car;
    }

    @Override
    public Class<?> getObjectType() {
        return Car.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    public String getCarInfos() {
        return carInfos;
    }

    public void setCarInfos(String carInfos) {
        this.carInfos = carInfos;
    }
}
