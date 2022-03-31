This directory contains the XML Schemas (XSD) used by the Android SDK Repository.

The repository exports all the packages that compose the SDK as well as
various manifest that define what is available in the repository.
The XML schemas available here allows clients to validate the manifests.

TODO:
- overview of schemas
- principles of design
- principles of evolution vs revision numbers
- naming convention
- using by "make sdk_repo"


Naming Convention
-----------------

Repository schemas are named sdk-type-N.xsd where
- type is either addon, addons-list or repository.
- N is the schema revision number, starting at 1 and increment with each revision.

Schemas can also be named -sdk-type-N.xsd.
The dash prefix means this schema is a *future* schema that is not yet
used in production. This allows the repository to test future schemas
before they are deployed.
