#!python3

import json
import argparse
import os

DUPPABLE = ['netherite_chestplate',
            'netherite_leggings',
            'netherite_boots',
            'netherite_helmet',
            'netherite_sword',
            'netherite_axe',
            'netherite_pickaxe',
            'netherite_shovel',
            'bow',
            'elytra'
            ]

def main():
    args = argparse.ArgumentParser()
    args.add_argument("-t", "--template", help="One recipe to use as a template")
    args.add_argument("-C", "--dir", help="directory where all the recipes go")
    args = args.parse_args()
    with open(os.path.join(args.dir, args.template), 'r') as template:
        template = json.load(template)

    for item in DUPPABLE:
        template["key"]["O"]["item"] = item
        template["result"]["item"] = item
        with open(os.path.join(args.dir, f"{item}.json"), "w") as f:
            json.dump(template,f,indent=2)

if __name__ == '__main__':
    main()
