import java.lang.annotation.Annotation;
import java.util.ArrayList;

public class address_translator {

	
		
	public int address_translate(int logical_addr) 
	{
		int page_no=(logical_addr&0xff00)>>>8;	
		return page_no;
		
	}
	public int get_physical_address(int logical_addr,int page_size,ArrayList<String> page_table) 
	{
		int physical_addr=0;
		
		int vpn=logical_addr/page_size;
		int offset=logical_addr%page_size;
		
		
		return physical_addr;
	}
	
	
	
	

	
	
	
	public static void main(String[] args) {

		SafeVarargs s=new SafeVarargs() {
			
			@Override
			public Class<? extends Annotation> annotationType() {
				// TODO Auto-generated method stub
				return null;
			}
		};
		System.out.println(s);
		System.out.println(s);
		
	}

}
