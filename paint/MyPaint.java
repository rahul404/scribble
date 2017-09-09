import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;


class MCanvas extends Canvas
{
	Color color;
	boolean fill;
	Vector v;
	Point prev,point;
	Base b;
	int redC,blueC,greenC;
	int count;//count here specifies the shape selected
	MCanvas()
	{
		b=null;
		count=1;
		color=Color.black;
		redC=blueC=greenC=0;
		fill=false;
		v=new Vector(5);
	}
	void initValue()
	{
		color=Color.black;
		fill=false;
		v.removeAllElements();
		System.out.println(v);
		prev=point=null;
		redC=greenC=blueC=0;
		b=null;
		count=1;
	}
	public void paint(Graphics g)
	{
		GTemp.g=g;
		int i=-1;
		Point p1=null,p2=null;
		Base t=null;
		System.out.println(v);
		for(i=0;i<v.size();i++)
		{
			System.out.println("hii inside paint ");
			t=(Base)v.elementAt(i);
			t.draw();
			System.out.println("hii after paint ");
		}
		if(b!=null && prev!=null && point!=null )
		{
			g.setColor(color);
			b.draw();
		}
	}
}

class MyCheck extends Checkbox
{
	MyCheck(String n,boolean s,CheckboxGroup cb,ItemListener sL)
	{
		super(n,s,cb);
		addItemListener(sL);
	}
}

public class MyPaint extends Frame
{
	MenuBar mBar;
	Menu file;
	MenuItem open,close,new1,save;
	MCanvas p;
	Panel opt;
	Checkbox chk;
	CheckboxGroup groupShape;
	CheckboxGroup groupColor;
	MyShapeListener sL;
	MyColorListener cL;
	Checkbox isFill;
	MyCheck line ;
	MyCheck rect ;
	MyCheck oval ;
	Checkbox red;
	Checkbox black;
	Checkbox blue;
	Checkbox green;
	public MyPaint()
	{
		ImageIcon ic=new ImageIcon("icon.PNG");
		setIconImage(ic.getImage());
		setSize(700,600);
		addWindowListener(new MyWindowListener(this));
		init();
		centerFrame();
		setVisible(true);
	}
	public void init()
	{
		opt=new Panel();
		add(opt,BorderLayout.NORTH);
		initCanvas();
		initMenu();
		initShape();
		initColor();
	}
	 private void centerFrame() 
	{

            Dimension windowSize = getSize();
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            Point centerPoint = ge.getCenterPoint();
            int dx = centerPoint.x - windowSize.width / 2;
            int dy = centerPoint.y - windowSize.height / 2;    
            setLocation(dx, dy);
    }
	void initCanvas()
	{
		p=new MCanvas();
		add(p);
		//following statements added mouseevent detection on Canvas
		MyListener r=new MyListener(p);
		p.addMouseListener(r);
		p.addMouseMotionListener(r);
	}
	void initShape()
	{
		groupShape=new CheckboxGroup();
		sL=new MyShapeListener(p,this);
		line =new MyCheck("Line",true,groupShape,sL);
		rect =new MyCheck("Rectangle",false,groupShape,sL);
		oval =new MyCheck("Oval",false,groupShape,sL);
		opt.add(line);
		opt.add(rect);
		opt.add(oval);
	}
	void initColor()
	{
		isFill=new Checkbox("Fill");
		isFill.addItemListener(new MyFillListener(p));
		groupColor=new CheckboxGroup();
		cL=new MyColorListener(p,this);
		red=new Checkbox("Red",false);
		blue=new Checkbox("Blue",false);
		green=new Checkbox("Green",false);
		black=new Checkbox("Black",true);
		red.addItemListener(cL);
		green.addItemListener(cL);
		blue.addItemListener(cL);
		black.addItemListener(cL);
		opt.add(black);
		opt.add(red);
		opt.add(blue);
		opt.add(green);
		opt.add(isFill);
	}
	void initMenu()
	{
		mBar=new MenuBar();
		file=new Menu("File");
		open =new MenuItem("Open");
		close = new MenuItem("Close");
		new1 =new MenuItem("New");
		save = new MenuItem("Save");
		MyActionListener mAL = new MyActionListener(p,this);
		open.addActionListener(mAL);
		close.addActionListener(mAL);
		save.addActionListener(mAL);
		new1.addActionListener(mAL);
		setMenuBar(mBar);
		mBar.add(file);
		file.add(new1);
		file.add(open);
		file.addSeparator();
		file.add(save);
		file.addSeparator();
		file.add(close);
	}
	void initValue()
	{
		black.setState(true);
		line.setState(true);
		isFill.setState(false);
	}
	public static void main(String args[])
	{
		new MyPaint();
	}
}







class MyFillListener implements ItemListener
{
	MCanvas ref;
	MyFillListener(MCanvas ob)
	{
		ref=ob;
	}
	public void itemStateChanged(ItemEvent ie)
	{
		if(ie.getStateChange()==ie.SELECTED)
			ref.fill=true;
		else
			ref.fill=false;
	}
}
class MyShapeListener implements ItemListener
{
	MCanvas  ref;
	MyPaint ref2;
	MyShapeListener(MCanvas ob, MyPaint ob2)
	{
		ref2=ob2;
		ref=ob;
	}
	public void itemStateChanged(ItemEvent ie)
	{
		MyCheck t=(MyCheck)ref2.groupShape.getSelectedCheckbox();
		if(t==ref2.rect)
			ref.count=2;
		else if(t==ref2.oval)
			ref.count=3;
		else 
			ref.count=1;
	}
}
class MyColorListener implements ItemListener
{
	MCanvas  ref;
	MyPaint ref2;
	MyColorListener(MCanvas ob, MyPaint ob2)
	{
		ref2=ob2;
		ref=ob;
	}
	public void itemStateChanged(ItemEvent ie)
	{
		Checkbox t=(Checkbox)ie.getItemSelectable();
		ref2.black.setState(false);
		if(t==ref2.red)
			if(t.getState())
				ref.redC=255;
			else
				ref.redC=0;
		else if(t==ref2.blue)
			if(t.getState())
				ref.blueC=255;
			else
				ref.blueC=0;
		else if(t==ref2.green)
			if(t.getState())
				ref.greenC=255;
			else
				ref.greenC=0;
		else
		{
			ref.greenC=ref.blueC=ref.redC=0;
			ref2.red.setState(false);
			ref2.blue.setState(false);
			ref2.green.setState(false);
			ref2.black.setState(true);
		}
		if(ref.greenC==0&&ref.blueC==0&&ref.redC==0)
		{
			ref.color=Color.black;
			ref2.black.setState(true);
		}
		ref.color=new Color(ref.redC,ref.greenC,ref.blueC);
	}
}
//this is the class for MouseMotionListener and MouseListener
class MyListener implements MouseListener,MouseMotionListener //listener associated with my applet
{
	MCanvas  ref;
	MyListener(MCanvas ob)
	{
		ref=ob;
	}
	public void  mouseEntered(MouseEvent me){}
	public void  mouseExited(MouseEvent me){}
	public void  mouseMoved(MouseEvent me){}
	public void mousePressed(MouseEvent me)
	{
		if(SwingUtilities.isRightMouseButton(me))
		{
			ref.b=null;
			ref.prev=ref.point=null;
			ref.repaint();
			return;
		}
		ref.prev=me.getPoint();
	}
	public void mouseDragged(MouseEvent me)
	{
		if(ref.prev==null)
		{
			ref.b=null;
			return;
		}
		ref.point=me.getPoint();
				switch (ref.count)
		{
//1=Line 2=Rectangle 3=Circle
		case 1:
			ref.b=new MyLine(ref.prev.x,ref.prev.y,ref.point.x,ref.point.y,ref.color);
			break;
		case 2:
			ref.b=new Rect(ref.prev.x,ref.prev.y,ref.point.x,ref.point.y,ref.color,ref.fill);
			break;
		case 3:
			ref.b=new Oval(ref.prev.x,ref.prev.y,ref.point.x,ref.point.y,ref.color,ref.fill);
			break;
		}
		ref.repaint();
	}
	public void mouseReleased(MouseEvent me)
	{
		if(ref.prev==null)
		{
			ref.b=null;
			return;
		}
		ref.v.addElement(ref.b);
		ref.repaint();
	}
	public void mouseClicked(MouseEvent me)
	{
		ref.b=null;
		ref.prev=ref.point=null;
		ref.v.removeElementAt(ref.v.size()-1);
	}
}
class MyWindowListener extends WindowAdapter
{
	MyPaint ref;
	MyWindowListener(MyPaint ob)
	{
		ref=ob;
	}
	public void windowClosing(WindowEvent we)
	{
		
		System.exit(1);
	}
}
class MyActionListener implements ActionListener 
{
	MCanvas ref;
	MyPaint ref2;
	MyActionListener(MCanvas ob, MyPaint ob2)
	{
		ref=ob;
		ref2=ob2;
	}
	public void actionPerformed(ActionEvent ae)
	{
		
		MenuItem source=(MenuItem)ae.getSource();
		if(source==ref2.save)
		{
			save();
			return;
		}
		if(source==ref2.new1)
		{
			ref.initValue();
			ref2.initValue();
			ref.repaint();
			return;
		}
		if(source==ref2.close)
			System.exit(1);
		open();
	}
	void open()
	{
		Color c=null;
		int r,g,b;
		int id,d1,d2,d3,d4;
		boolean flag=false;
		r=b=g=id=d1=d2=d3=d4=0;
		Rect rt=null;
		MyLine lt=null;
		Oval ot=null;
		try
		{
			RandomAccessFile f=new RandomAccessFile("rahul.txt","r");
			if(f.length()==0)
				return;
			ref.initValue();
			ref2.initValue();
			while(f.getFilePointer()<f.length())
			{
				id=Integer.parseInt( f.readLine() );

				d1=Integer.parseInt( f.readLine() );

				d2=Integer.parseInt( f.readLine() );

				d3=Integer.parseInt( f.readLine() );

				d4=Integer.parseInt( f.readLine() );

				r=Integer.parseInt( f.readLine() );

				g=Integer.parseInt( f.readLine() );

				b=Integer.parseInt( f.readLine() );

				flag=Boolean.parseBoolean( f.readLine() );
				System.out.println("id= "+id+"d1= "+d1+"d2= "+d2+"d3= "+d3+"d4= "+d4+"r= "+r);

				c=new Color(r,g,b);
				switch(id)
				{
					case 0:
						lt=new MyLine(d1,d2,d3,d4,c);
						ref.v.addElement( lt );
						break;
					case 1:
						rt=new Rect(d1,d2,d3,d4,c,flag);
						ref.v.addElement( rt );
						break;
					case 2:
						ot=new Oval(d1,d2,d3,d4,c,flag);
						ref.v.addElement( ot );
				}
			}
			System.out.println(ref.v);
			ref.repaint();
		}
		catch (Exception e)
		{
			System.out.println("OPEN ERROR");
		}
	}
	void save()
	{
		try
		{
			FileWriter f =new FileWriter("rahul.txt");
			BufferedWriter out=new BufferedWriter(f);
			Base temp;
			System.out.println("\n\n"+ref+"\n\n\n\n");
			for(int i=0;i<ref.v.size();i++)
			{
				temp=(Base)ref.v.elementAt(i);
				out.write( String.valueOf( temp.id) );
				out.write("\r\n");
				out.write( String.valueOf( temp.d1) );
				out.write("\r\n");
				out.write( String.valueOf( temp.d2) );
				out.write("\r\n");
				out.write( String.valueOf( temp.d3) );
				out.write("\r\n");
				out.write( String.valueOf( temp.d4) );
				out.write("\r\n");
				out.write( String.valueOf( temp.c.getRed()) );
				out.write("\r\n");
				out.write( String.valueOf( temp.c.getGreen()) );
				out.write("\r\n");
				out.write( String.valueOf( temp.c.getBlue()) );
				out.write("\r\n");
				out.write( String.valueOf( temp.fill) );
				out.write("\r\n");
				System.out.println("id= "+temp.id+"d1= "+temp.d1+"d2= "+temp.d2+"d3= "+temp.d3+"d4= "+temp.d4+"r= "+temp.c.getRed());
			}
			out.close();
		}
		catch (IOException e)
		{
			System.out.println("ERROR");
		}
	}
}








abstract class Base//i have used this interface because i want all the classes to have this method
{
	int id,d1,d2,d3,d4;
	Color c;
	boolean fill;
	Base(Color x,boolean f)
	{
		c=x;
		fill=f;
	}
	abstract void draw();
}
class Rect extends Base//draws Rectangle takes inout as two co-ordinates
{
	int top,left,height,width;
	Rect(int x,int y,int dx,int dy,Color z,boolean f)
	{
		super(z,f);
		id=1;
		top=x<dx?x:dx;
		left=y<dy?y:dy;
		height=Math.abs(dy-y);
		width=Math.abs(dx-x);
		d1=top;
		d2=left;
		d3=dx;
		d4=dy;
	}
	public void draw()
	{
		GTemp.g.setColor(c);
		if(fill)
			GTemp.g.fillRect(top,left,width,height);
		else
			GTemp.g.drawRect(top,left,width,height);
	}
}
class MyLine extends Base //draws line takes inout as two co-ordinates
{
	int x1,x2,y1 ,y2;
	MyLine(int tx1,int ty1,int tx2,int ty2,Color z)
	{
		super(z,false);
		id=0;
		x1=tx1;
		y1=ty1;
		x2=tx2;
		y2=ty2;
		d1=x1;
		d2=y1;
		d3=x2;
		d4=y2;
	}
	public void draw()
	{
		GTemp.g.setColor(c);
		GTemp.g.drawLine(x1,y1,x2,y2);
	}
}
class Oval extends Base //draws oval takes inout as two co-ordinates
{
	int top,left,width,height;
	Oval(int x,int y,int dx,int dy,Color z,boolean f)
	{
		super(z,f);
		id=2;
		top=x<dx?x:dx;
		left=y<dy?y:dy;
		height=Math.abs(dy-y);
		width=Math.abs(dx-x);
		d1=top;
		d2=left;
		d3=dx;
		d4=dy;	
	}
	public void draw()
	{
		GTemp.g.setColor(c);
		if(fill)
			GTemp.g.fillOval(top,left,width,height);
		else
			GTemp.g.drawOval(top,left,width,height);
	}
}
class GTemp //for access to graphics object
{
	static Graphics g;
}/*
class CloseDialog extends Dialog implements ActionListener
{
	NotePad ref;//for reference to notepad obj
	Label l;//label to display message of the dialogbox
	Button save,dontSave,cancel;//buttons for control on dialogbox
	CloseDialog(Frame parent)
	{
		super(parent,true);
		ref=(NotePad)parent;
		setLayout(new BorderLayout(10,10));
		l=new Label("File not saved");
		save=new Button("save");
		dontSave=new Button("Don't Save");
		cancel=new Button("cancel");
		l.setBackground(Color.white);
		add(l,BorderLayout.NORTH);
		add(save,BorderLayout.WEST);
		add(dontSave,BorderLayout.CENTER);
		add(cancel,BorderLayout.EAST);
		save.addActionListener(this);
		dontSave.addActionListener(this);
		cancel.addActionListener(this);
		setSize(200,100);
		addWindowListener(new MyWindowAdapter(this));
		centerFrame();
		setVisible(true);
	}
	private void centerFrame() 
	{

            Dimension windowSize = getSize();
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            Point centerPoint = ge.getCenterPoint();

            int dx = centerPoint.x - windowSize.width / 2;
            int dy = centerPoint.y - windowSize.height / 2;    
            setLocation(dx, dy);
    }
	public void actionPerformed(ActionEvent ae)
	{
		Button source=(Button)ae.getSource();
		if(source==cancel)
		{
			dispose();
			return;
		}
		if(source==save)
			ref.save();
		ref.dispose();
	}
}
*/