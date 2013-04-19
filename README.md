APIHelper
=========

A set of methods to help me not have to rewrite API interactions in the future.  First stab at it.  May be a better way.

How to:

* Reference the library in your project.
* Create a BroadcastReceiver, where necessary, to listen for results.
* Pass a url and parameters to an api method.
* See example project for details.

Future:

* Funnel all calls through a gatekeeper method to reduce duplicated code.
* Set up a queue to handle gaps in connectivity.

---


Next

* Listen for network status change.  Use that to set a boolean in NetworkStatusHelper.
* If no network, queue.
* If network returns, run queue.