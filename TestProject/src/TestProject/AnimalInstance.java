package TestProject;

class Cat extends Animal{
	
	public void Sleep() {
		System.out.println("スース―");
	}
	public void Speak() {
		System.out.println("ニャー");
	}
}

class Dog extends Animal {
	public void Run() {
		System.out.println("トコトコ");
	}
	public void Speak() {
		System.out.println("ワンワン");
	}
}
public class AnimalInstance {
	
	public static void main(String[] args) {
		
		Animal[] animal = new Animal[4];
		
		animal[0] = new Cat();
		animal[1] = new Dog();	
		animal[2] = new Cat();
		animal[3] = new Dog();
		
		for(Animal pet : animal) {
			pet.Speak();
		}
			
	}
}