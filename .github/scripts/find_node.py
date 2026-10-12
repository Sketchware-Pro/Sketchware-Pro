"""Print the center "x y" of the first UI node whose resource-id ends with ':id/<name>'.

Usage: find_node.py <uiautomator-dump.xml> <name>
Exits with status 1 when no such node exists.
"""
import re
import sys
import xml.etree.ElementTree as ET

if len(sys.argv) != 3:
    sys.exit(2)

dump_path, name = sys.argv[1], sys.argv[2]
tree = ET.parse(dump_path)
suffix = ":id/" + name

for node in tree.iter("node"):
    rid = node.get("resource-id", "")
    if rid.endswith(suffix):
        x1, y1, x2, y2 = map(int, re.findall(r"\d+", node.get("bounds", "")))
        if x2 > x1 and y2 > y1:
            print((x1 + x2) // 2, (y1 + y2) // 2)
            sys.exit(0)

sys.exit(1)
