package com.min.aspects;

public aspect ServiceAspect {

	/*	private Vector<Point> UserService.observers = new Vector<Point>();*/
	/*	
	pointcut callServiceTest() : call(public void com.min.service.*Service.test*());

	before() : callServiceTest() {
		System.out.println("before!");
	}

	after() : callServiceTest() {
		System.out.println("after!");
	}*/
	/*	
	public static void addObserver(UserService userService,Point p) {
		userService.observers.add(p);
	}

	public static void removeObserver(UserService userService,Point p) {
		userService.observers.remove(p);
	}

	pointcut changes(UserService p): target(p) && call(void com.min.service.UserService.test*());

	@SuppressWarnings("rawtypes")
	after(UserService p): changes(p){
		Iterator it = p.observers.iterator();
		while(it.hasNext()) {
			System.out.println(thisJoinPoint);
		}
	}*/
/*
	pointcut mycflow(): cflow(call (void com.min.**.*.method*(..)));

	pointcut myThis(): this(com.min.service.UserService);

	after(): myThis() && !target(com.min.service.UserService) {
		System.out.println("afeter myThis");
	}

	pointcut myTarget(): target(com.min.service.RoleService);

	after(): myTarget() {
		System.out.println("after myTarget");
	}

	public int com.min.service.RoleService.getX(){ 
		return this.x;
	}

	private int com.min.service.RoleService.x;

	declare parents: com.min.service.RoleService implements Comparable;

	public int com.min.service.RoleService.compareTo(Object other){
		return -1;
	}*/
	
	pointcut trace(): execution (* *(..)) && !within(com.min.TheMain);

	before() : trace() {
		System.out.println(thisJoinPoint);
	}

}
