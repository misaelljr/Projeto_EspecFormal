package MEF;

import java.util.ArrayList;
import java.util.List;

public class Sequencias {
	List<String> seq = new ArrayList<String>();
	
	public Sequencias (){
		
	}
	
	public List<String> getSeq() {
		return seq;
	}
	
	public void setSeq(List<String> seq) {
		this.seq = seq;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return seq.toString();
	}

}
