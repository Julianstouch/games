package robhop.supply;


public interface IAction
{

    Boolean condition();

    void action();

    IAction setNext(IAction theNext);
    
    IAction getNext();
    
    void setHead(IAction theHead);
    
    IAction getHead();
}
