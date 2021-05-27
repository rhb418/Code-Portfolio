# API Changes
## Changes from the Plan
- Treat Action as distince classes represented by strings and payload. Now use the ActionManager to manage them
- The view will not communicate to the model directly. All it will do is observe
  changes that happen, but if it needs to tell the model something it will do it
  through the controller. -> View act as a proxy (pattern) between model and view.