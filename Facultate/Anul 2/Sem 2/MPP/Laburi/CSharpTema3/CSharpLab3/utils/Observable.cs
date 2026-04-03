namespace CSharpLab3.utils;

public abstract class Observable
{
    private readonly List<IObserver> _observers = new();

    public void AddObserver(IObserver observer) => _observers.Add(observer);
    public void RemoveObserver(IObserver observer) => _observers.Remove(observer);
    public List<IObserver> GetObservers() => _observers;
    protected void NotifyObservers() => _observers.ForEach(o => o.Update());
}