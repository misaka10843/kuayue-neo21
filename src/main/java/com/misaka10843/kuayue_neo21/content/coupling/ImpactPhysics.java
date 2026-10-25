package com.misaka10843.kuayue_neo21.content.coupling;

public final class ImpactPhysics {
    public record Result(double firstSpeed, double secondSpeed, double restitution) {}

    private ImpactPhysics() {}

    public static Result resolve(double firstMass, double secondMass, double firstSpeed, double secondSpeed) {
        if (!Double.isFinite(firstMass) || !Double.isFinite(secondMass) || firstMass <= 0 || secondMass <= 0
                || !Double.isFinite(firstSpeed) || !Double.isFinite(secondSpeed)) throw new IllegalArgumentException("Invalid collision input");
        double relativeSpeed = Math.abs(firstSpeed - secondSpeed);
        double restitution = relativeSpeed <= 0.4 ? 1 : 0.4 / relativeSpeed;
        double impulse = -(1 + restitution) * (firstSpeed - secondSpeed) / (1 / firstMass + 1 / secondMass);
        return new Result(firstSpeed + impulse / firstMass, secondSpeed - impulse / secondMass, restitution);
    }
}
