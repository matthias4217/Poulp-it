/**
 * 
 */
package content;

/**
 * @author Raphaël
 * Interface for all behavior scripts that can be added to a gameObject.
 */
public interface MonoBehavior {
	void Awake();
	void Start();
	void Update();
	void LateUpdate();
	
}
